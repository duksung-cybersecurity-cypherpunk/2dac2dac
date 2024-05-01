package dac2dac.doctect.agency.service;

import dac2dac.doctect.agency.dto.request.UserLocationDto;
import dac2dac.doctect.agency.dto.response.HospitalPreviewDto;
import dac2dac.doctect.agency.dto.response.HospitalPreviewListDto;
import dac2dac.doctect.agency.dto.response.PharmacyPreviewDto;
import dac2dac.doctect.agency.dto.response.PharmacyPreviewListDto;
import dac2dac.doctect.agency.entity.Agency;
import dac2dac.doctect.agency.entity.Hospital;
import dac2dac.doctect.agency.entity.Pharmacy;
import dac2dac.doctect.agency.repository.HospitalRepository;
import dac2dac.doctect.agency.repository.PharmacyRepository;
import dac2dac.doctect.common.constant.ErrorCode;
import dac2dac.doctect.common.error.exception.NotFoundException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AgencyService {

    private final PharmacyRepository pharmacyRepository;
    private final HospitalRepository hospitalRepository;

    public PharmacyPreviewListDto searchAgency(UserLocationDto userLocationDto) {
        PharmacyPreviewListDto pharmacyPreviewList = getNearbyPharmacy(userLocationDto);
        HospitalPreviewListDto hospitalPreviewList = getNearbyHospital(userLocationDto);

        return pharmacyPreviewList;
    }

    public HospitalPreviewListDto getNearbyHospital(UserLocationDto userLocationDto) {
        double radius = 2.0;

        List<Hospital> nearbyHospitals = hospitalRepository.findNearbyHospitals(userLocationDto.getLatitude(), userLocationDto.getLongitude(), radius);
        if (nearbyHospitals.isEmpty()) {
            throw new NotFoundException(ErrorCode.NEARBY_HOSPITAL_NOT_FOUND);
        }

        List<HospitalPreviewDto> hospitalPreviewDtoList = nearbyHospitals.stream()
            .map(h -> createHospitalPreviewDto(h, userLocationDto))
            .collect(Collectors.toList());

        HospitalPreviewListDto hospitalPreviewListDto = HospitalPreviewListDto.builder()
            .totalCnt(hospitalPreviewDtoList.size())
            .hospitalPreviewList(hospitalPreviewDtoList)
            .build();

        return hospitalPreviewListDto;
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

    private HospitalPreviewDto createHospitalPreviewDto(Hospital h, UserLocationDto userLocation) {
        return HospitalPreviewDto.builder()
            .name(h.getName())
            .todayOpenTime(findTodayOpenTime(h))
            .todayCloseTime(findTodayCloseTime(h))
            .isOpen(isAgencyOpen(h))
            .distance(calculateDistance(h.getLatitude(), h.getLongitude(), userLocation.getLatitude(), userLocation.getLongitude()))
            .longtitude(h.getLongitude())
            .latitude(h.getLatitude())
            .build();
    }

    private PharmacyPreviewDto createPharmacyPreviewDto(Pharmacy p, UserLocationDto userLocation) {
        return PharmacyPreviewDto.builder()
            .name(p.getName())
            .todayOpenTime(findTodayOpenTime(p))
            .todayCloseTime(findTodayCloseTime(p))
            .isOpen(isAgencyOpen(p))
            .address(p.getAddress())
            .tel(p.getTel())
            .distance(calculateDistance(p.getLatitude(), p.getLongitude(), userLocation.getLatitude(), userLocation.getLongitude()))
            .longtitude(p.getLongitude())
            .latitude(p.getLatitude())
            .build();
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        lat1 = Math.toRadians(lat1);
        lon1 = Math.toRadians(lon1);
        lat2 = Math.toRadians(lat2);
        lon2 = Math.toRadians(lon2);

        double earthRadius = 6371; // Kilometers
        return earthRadius * Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));
    }

    private static <T> boolean isAgencyOpen(Agency t) {
        Integer todayOpenTime = findTodayOpenTime(t);
        Integer todayCloseTime = findTodayCloseTime(t);

        if (todayOpenTime != null && todayCloseTime != null) {
            // LocalTime으로 변환
            LocalTime startTime = LocalTime.parse(String.format("%04d", findTodayOpenTime(t)), DateTimeFormatter.ofPattern("HHmm"));
            LocalTime endTime = LocalTime.parse(String.format("%04d", findTodayCloseTime(t)), DateTimeFormatter.ofPattern("HHmm"));
            LocalTime now = LocalTime.now();

            // 현재 시간이 오픈 시간과 클로즈 시간 사이에 있는지 확인
            return !now.isBefore(startTime) && now.isBefore(endTime);
        } else {
            return false;
        }
    }

    private static Integer findTodayOpenTime(Agency agency) {
        LocalDate today = LocalDate.now();
        DayOfWeek dayOfWeek = today.getDayOfWeek();

        switch (dayOfWeek) {
            case MONDAY:
                return agency.getDiagTimeMonOpen();
            case TUESDAY:
                return agency.getDiagTimeTuesOpen();
            case WEDNESDAY:
                return agency.getDiagTimeWedsOpen();
            case THURSDAY:
                return agency.getDiagTimeThursOpen();
            case FRIDAY:
                return agency.getDiagTimeFriOpen();
            case SATURDAY:
                return agency.getDiagTimeSatOpen();
            case SUNDAY:
                return agency.getDiagTimeSunOpen();
        }
        return 0;
    }

    private static Integer findTodayCloseTime(Agency agency) {
        LocalDate today = LocalDate.now();
        DayOfWeek dayOfWeek = today.getDayOfWeek();

        switch (dayOfWeek) {
            case MONDAY:
                return agency.getDiagTimeMonClose();
            case TUESDAY:
                return agency.getDiagTimeTuesClose();
            case WEDNESDAY:
                return agency.getDiagTimeWedsClose();
            case THURSDAY:
                return agency.getDiagTimeThursClose();
            case FRIDAY:
                return agency.getDiagTimeFriClose();
            case SATURDAY:
                return agency.getDiagTimeSatClose();
            case SUNDAY:
                return agency.getDiagTimeSunClose();
        }
        return 0;
    }
}
