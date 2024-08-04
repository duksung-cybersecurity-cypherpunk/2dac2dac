package dac2dac.doctect.agency.service;

import dac2dac.doctect.agency.entity.Hospital;
import dac2dac.doctect.agency.repository.HospitalRepository;
import dac2dac.doctect.agency.vo.HospitalItems;
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
public class HospitalService {

    private final WebClient webClient;
    private final HospitalRepository hospitalRepository;

    @Value("${open-api.hospital.key}")
    private String HOSPITAL_API_KEY;

    @Value("${open-api.hospital.endpoint}")
    private String HOSPITAL_ENDPOINT;

    @Async
    public void saveHospitalInfo(int pageNo) {
        HospitalItems hospitalItems = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(HOSPITAL_ENDPOINT)
                        .queryParam("serviceKey", HOSPITAL_API_KEY)
                        .queryParam("_type", "json")
                        .queryParam("pageNo", pageNo)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(HospitalItems.class)
                .retry(3)
                .block();

        try {
            hospitalItems.getHospitalItems().forEach(item -> {
                Hospital hospital = item.toEntity();
                hospitalRepository.save(hospital);
            });
        } catch (DataIntegrityViolationException e) {
        }

        log.info("pageNo: {} :: hospitalItems: {}", pageNo, hospitalItems);
    }
}