package dac2dac.doctect.agency.service;

import dac2dac.doctect.agency.dto.request.UserLocationDto;
import dac2dac.doctect.agency.dto.response.AgencySearchResultDto;
import dac2dac.doctect.agency.dto.response.AgencySearchResultListDto;
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
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AgencyService {

    private final PharmacyRepository pharmacyRepository;
    private final HospitalRepository hospitalRepository;

    public AgencySearchResultListDto searchAgency(UserLocationDto userLocationDto) {
        AgencySearchResultListDto pharmacyPreviewList = getNearbyPharmacy(userLocationDto);
        AgencySearchResultListDto hospitalPreviewList = getNearbyHospital(userLocationDto);

        List<AgencySearchResultDto> agencySearchResultList = Stream.concat(
                pharmacyPreviewList.getAgencySearchResultList().stream(),
                hospitalPreviewList.getAgencySearchResultList().stream()
            )
            .sorted(Comparator.comparing(AgencySearchResultDto::getDistance))
            .collect(Collectors.toList());

        return AgencySearchResultListDto.builder()
            .totalCnt(agencySearchResultList.size())
            .agencySearchResultList(agencySearchResultList)
            .build();
    }

    public AgencySearchResultListDto getNearbyHospital(UserLocationDto userLocationDto) {
        double radius = 2.0;

        List<Hospital> nearbyHospitals = hospitalRepository.findNearbyHospitals(userLocationDto.getLatitude(), userLocationDto.getLongitude(), radius);
        if (nearbyHospitals.isEmpty()) {
            throw new NotFoundException(ErrorCode.NEARBY_HOSPITAL_NOT_FOUND);
        }

        List<AgencySearchResultDto> hospitalPreviewDtoList = nearbyHospitals.stream()
            .map(h -> createAgencySearchResultDto(h, userLocationDto))
            .collect(Collectors.toList());

        AgencySearchResultListDto hospitalPreviewListDto = AgencySearchResultListDto.builder()
            .totalCnt(hospitalPreviewDtoList.size())
            .agencySearchResultList(hospitalPreviewDtoList)
            .build();

        return hospitalPreviewListDto;
    }

    public AgencySearchResultListDto getNearbyPharmacy(UserLocationDto userLocationDto) {
        double radius = 2.0; // 검색 반경 설정 (예: 2.0km)

        List<Pharmacy> nearbyPharmacies = pharmacyRepository.findNearbyPharmacies(userLocationDto.getLatitude(), userLocationDto.getLongitude(), radius);
        if (nearbyPharmacies.isEmpty()) {
            throw new NotFoundException(ErrorCode.NEARBY_PHARMACY_NOT_FOUND);
        }

        List<AgencySearchResultDto> pharmacyPreviewDtoList = nearbyPharmacies.stream()
            .map(p -> createAgencySearchResultDto(p, userLocationDto))
            .collect(Collectors.toList());

        AgencySearchResultListDto pharmacyPreviewListDto = AgencySearchResultListDto.builder()
            .totalCnt(pharmacyPreviewDtoList.size())
            .agencySearchResultList(pharmacyPreviewDtoList)
            .build();

        return pharmacyPreviewListDto;
    }

    private AgencySearchResultDto createAgencySearchResultDto(Agency a, UserLocationDto userLocation) {
        return AgencySearchResultDto.builder()
            .name(a.getName())
            .todayOpenTime(findTodayOpenTime(a))
            .todayCloseTime(findTodayCloseTime(a))
            .isOpen(isAgencyOpen(a))
            .address(a.getAddress())
            .tel(a.getTel())
            .distance(calculateDistance(a.getLatitude(), a.getLongitude(), userLocation.getLatitude(), userLocation.getLongitude()))
            .longtitude(a.getLongitude())
            .latitude(a.getLatitude())
            .agencyType(a.getAgencyType())
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

    private static <T> boolean isAgencyOpen(Agency agency) {
        Integer todayOpenTime = findTodayOpenTime(agency);
        Integer todayCloseTime = findTodayCloseTime(agency);

        if (todayOpenTime != null && todayCloseTime != null) {
            // LocalTime으로 변환
            LocalTime startTime = LocalTime.parse(String.format("%04d", findTodayOpenTime(agency)), DateTimeFormatter.ofPattern("HHmm"));
            LocalTime endTime = LocalTime.parse(String.format("%04d", findTodayCloseTime(agency)), DateTimeFormatter.ofPattern("HHmm"));
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
