package dac2dac.doctect.agency.service;

import dac2dac.doctect.agency.dto.request.UserLocationDto;
import dac2dac.doctect.agency.dto.response.PharmacyPreviewDto;
import dac2dac.doctect.agency.dto.response.PharmacyPreviewListDto;
import dac2dac.doctect.agency.entity.Pharmacy;
import dac2dac.doctect.agency.repository.PharmacyRepository;
import dac2dac.doctect.agency.vo.PharmacyItems;
import dac2dac.doctect.common.constant.ErrorCode;
import dac2dac.doctect.common.error.exception.NotFoundException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
@Slf4j
public class PharmacyService {

    private final WebClient webClient;
    private final PharmacyRepository pharmacyRepository;

    @Value("${open-api.pharmacy.key}")
    private String PHARMACY_API_KEY;

    @Value("${open-api.pharmacy.endpoint}")
    private String PHARMACY_ENDPOINT;

    @Async
    public void savePharmacyInfo(int pageNo) {
        PharmacyItems pharmacyItems = webClient.get()
            .uri(uriBuilder -> uriBuilder
                .path(PHARMACY_ENDPOINT)
                .queryParam("serviceKey", PHARMACY_API_KEY)
                .queryParam("_type", "json")
                .queryParam("pageNo", pageNo)
                .build())
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(PharmacyItems.class)
            .retry(3)
            .block();

        try {
            pharmacyItems.getPharmacyItems().forEach(item -> {
                Pharmacy pharmacy = item.toEntity();
                pharmacyRepository.save(pharmacy);
            });
        } catch (DataIntegrityViolationException e) {
        }

        log.info("pageNo: {} :: pharmacyItems: {}", pageNo, pharmacyItems);
    }

    public PharmacyPreviewListDto getNearbyPharmacy(UserLocationDto userLocationDto) {
        double radius = 2.0; // 검색 반경 설정 (예: 2.0km)

        List<Pharmacy> nearbyPharmacies = pharmacyRepository.findNearbyPharmacies(userLocationDto.getLatitude(), userLocationDto.getLongitude(), radius);
        if (nearbyPharmacies.isEmpty()) {
            throw new NotFoundException(ErrorCode.NEARBY_PHARMACY_NOT_FOUND);
        }

        List<PharmacyPreviewDto> pharmacyPreviewDtoList = nearbyPharmacies.stream()
            .map(p -> createPharmacyPreviewDto(p, userLocationDto))
            .collect(Collectors.toList());

        PharmacyPreviewListDto pharmacyPreviewListDto = PharmacyPreviewListDto.builder()
            .totalCnt(pharmacyPreviewDtoList.size())
            .pharmacyPreviewList(pharmacyPreviewDtoList)
            .build();

        return pharmacyPreviewListDto;
    }

    private PharmacyPreviewDto createPharmacyPreviewDto(Pharmacy p, UserLocationDto userLocation) {
        return PharmacyPreviewDto.builder()
            .name(p.getName())
            .todayOpenTime(findTodayOpenTime(p))
            .todayCloseTime(findTodayCloseTime(p))
            .isOpen(isPharmacyOpen(p))
            .distance(calculateDistance(p.getLatitude(), p.getLongitude(), userLocation.getLatitude(), userLocation.getLongitude()))
            .address(p.getAddress())
            .tel(p.getTel())
            .build();
    }

    private boolean isPharmacyOpen(Pharmacy p) {
        Integer todayOpenTime = findTodayOpenTime(p);
        Integer todayCloseTime = findTodayCloseTime(p);

        if (todayOpenTime != null && todayCloseTime != null) {
            // LocalTime으로 변환
            LocalTime startTime = LocalTime.parse(String.format("%04d", findTodayOpenTime(p)), DateTimeFormatter.ofPattern("HHmm"));
            LocalTime endTime = LocalTime.parse(String.format("%04d", findTodayCloseTime(p)), DateTimeFormatter.ofPattern("HHmm"));
            LocalTime now = LocalTime.now();

            // 현재 시간이 오픈 시간과 클로즈 시간 사이에 있는지 확인
            return !now.isBefore(startTime) && now.isBefore(endTime);
        } else {
            return false;
        }
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        lat1 = Math.toRadians(lat1);
        lon1 = Math.toRadians(lon1);
        lat2 = Math.toRadians(lat2);
        lon2 = Math.toRadians(lon2);

        double earthRadius = 6371; // Kilometers
        return earthRadius * Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));
    }

    private Integer findTodayOpenTime(Pharmacy p) {
        LocalDate today = LocalDate.now();
        DayOfWeek dayOfWeek = today.getDayOfWeek();

        switch (dayOfWeek) {
            case MONDAY:
                return p.getDiagTimeMonOpen();
            case TUESDAY:
                return p.getDiagTimeTuesOpen();
            case WEDNESDAY:
                return p.getDiagTimeWedsOpen();
            case THURSDAY:
                return p.getDiagTimeThursOpen();
            case FRIDAY:
                return p.getDiagTimeFriOpen();
            case SATURDAY:
                return p.getDiagTimeSatOpen();
            case SUNDAY:
                return p.getDiagTimeSunOpen();
        }
        return 0;
    }

    private Integer findTodayCloseTime(Pharmacy p) {
        LocalDate today = LocalDate.now();
        DayOfWeek dayOfWeek = today.getDayOfWeek();

        switch (dayOfWeek) {
            case MONDAY:
                return p.getDiagTimeMonClose();
            case TUESDAY:
                return p.getDiagTimeTuesClose();
            case WEDNESDAY:
                return p.getDiagTimeWedsClose();
            case THURSDAY:
                return p.getDiagTimeThursClose();
            case FRIDAY:
                return p.getDiagTimeFriClose();
            case SATURDAY:
                return p.getDiagTimeSatClose();
            case SUNDAY:
                return p.getDiagTimeSunClose();
        }
        return 0;
    }
}