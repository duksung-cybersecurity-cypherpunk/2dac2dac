package dac2dac.doctect.agency.service;

import dac2dac.doctect.agency.dto.request.SearchCriteria;
import dac2dac.doctect.agency.dto.response.AgencySearchResultDto;
import dac2dac.doctect.agency.dto.response.AgencySearchResultListDto;
import dac2dac.doctect.agency.entity.Agency;
import dac2dac.doctect.agency.repository.HospitalRepository;
import dac2dac.doctect.agency.repository.PharmacyRepository;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AgencyService {

    private final PharmacyRepository pharmacyRepository;
    private final HospitalRepository hospitalRepository;

    public AgencySearchResultListDto searchAgency(SearchCriteria criteria) {
        final double latitude = criteria.getLatitude();
        final double longitude = criteria.getLongitude();
        final double radius = 2.0;

        Set<AgencySearchResultDto> searchResultSet = new HashSet<>(); // 중복 제거를 위한 Set

        if (criteria.getKeyword() != null && !criteria.getKeyword().isEmpty()) { // 검색명 있는 경우
            if (criteria.isHospital()) { // 병원 필터 적용
                hospitalRepository.findNearbyHospitalsWithKeyword(latitude, longitude, radius, criteria.getKeyword())
                    .stream()
                    .map(h -> createAgencySearchResultDto(h, latitude, longitude))
                    .forEach(searchResultSet::add);
            }
            if (criteria.isPharmacy()) { // 약국 필터 적용
                pharmacyRepository.findNearbyPharmaciesWithKeyword(latitude, longitude, radius, criteria.getKeyword())
                    .stream()
                    .map(p -> createAgencySearchResultDto(p, latitude, longitude))
                    .forEach(searchResultSet::add);
            }
            if (criteria.isEr()) { // 응급실 필터 적용
                hospitalRepository.findNearbyERsWithKeyword(latitude, longitude, radius, criteria.getKeyword())
                    .stream()
                    .map(p -> createAgencySearchResultDto(p, latitude, longitude))
                    .forEach(searchResultSet::add);
            }
        } else { // 검색어 없는 경우
            if (criteria.isHospital()) { // 병원 필터 적용
                hospitalRepository.findNearbyHospitals(latitude, longitude, radius)
                    .stream()
                    .map(h -> createAgencySearchResultDto(h, latitude, longitude))
                    .forEach(searchResultSet::add);
            }
            if (criteria.isPharmacy()) { // 약국 필터 적용
                pharmacyRepository.findNearbyPharmacies(latitude, longitude, radius)
                    .stream()
                    .map(p -> createAgencySearchResultDto(p, latitude, longitude))
                    .forEach(searchResultSet::add);
            }
            if (criteria.isEr()) { // 응급실 필터 적용
                hospitalRepository.findNearbyERs(latitude, longitude, radius)
                    .stream()
                    .map(p -> createAgencySearchResultDto(p, latitude, longitude))
                    .forEach(searchResultSet::add);
            }
        }

        // Set에서 중복 제거 후 List로 변환
        List<AgencySearchResultDto> searchResult = new ArrayList<>(searchResultSet);

        // 영업 시간 필터링

        // 가까운순 정렬
        List<AgencySearchResultDto> sortedSearchResult = searchResult.stream()
            .sorted(Comparator.comparing(AgencySearchResultDto::getDistance))
            .collect(Collectors.toList());

        return AgencySearchResultListDto.builder()
            .totalCnt(sortedSearchResult.size())
            .agencySearchResultList(sortedSearchResult)
            .build();
    }

    private AgencySearchResultDto createAgencySearchResultDto(Agency a, double latitude, double longitude) {
        return AgencySearchResultDto.builder()
            .name(a.getName())
            .todayOpenTime(findTodayOpenTime(a))
            .todayCloseTime(findTodayCloseTime(a))
            .isOpen(isAgencyOpen(a))
            .address(a.getAddress())
            .tel(a.getTel())
            .distance(calculateDistance(a.getLatitude(), a.getLongitude(), latitude, longitude))
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
