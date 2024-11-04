package dac2dac.doctect.doctor.service;

import dac2dac.doctect.doctor.entity.Medicine;
import dac2dac.doctect.doctor.repository.MedicineRepository;
import dac2dac.doctect.doctor.vo.MedicineItems;
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
public class MedicineService {
    private final WebClient webClient;
    private final MedicineRepository medicineRepository;

    @Value("${open-api.medicine.key}")
    private String MEDICINE_API_KEY;

    @Value("${open-api.medicine.endpoint}")
    private String MEDICINE_ENDPOINT;

    @Async
    public void saveHospitalInfo(int pageNo) {
        MedicineItems medicineItems = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(MEDICINE_ENDPOINT)
                        .queryParam("serviceKey", MEDICINE_API_KEY)
                        .queryParam("type", "json")
                        .queryParam("pageNo", pageNo)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(MedicineItems.class)
                .retry(3)
                .block();

        try {
            medicineItems.getMedicineItems().forEach(item -> {
                Medicine medicine = item.toEntity();
                medicineRepository.save(medicine);
            });
        } catch (DataIntegrityViolationException e) {
        }

        log.info("pageNo: {} :: hospitalItems: {}", pageNo, medicineItems);
    }

}
