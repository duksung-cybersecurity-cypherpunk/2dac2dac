package dac2dac.doctect.common.component;

import dac2dac.doctect.agency.service.HospitalService;
import dac2dac.doctect.doctor.service.MedicineService;
import dac2dac.doctect.agency.service.PharmacyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
@Slf4j
public class OpenApiDataLoader implements ApplicationRunner {

    private final PharmacyService pharmacyService;
    private final HospitalService hospitalService;
    private final MedicineService medicineService;
    private final WebClient webClient;

    @Value("${open-api.pharmacy.key}")
    private String PHARMACY_API_KEY;

    @Value("${open-api.pharmacy.endpoint}")
    private String PHARMACY_ENDPOINT;

    @Value("${open-api.hospital.key}")
    private String HOSPITAL_API_KEY;

    @Value("${open-api.hospital.endpoint}")
    private String HOSPITAL_ENDPOINT;

    @Value("${open-api.medicine.key}")
    private String MEDICINE_API_KEY;

    @Value("${open-api.medicine.endpoint}")
    private String MEDICINE_ENDPOINT;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        saveAllPharmacyInfo();
        saveAllHospitalInfo();
        saveAllMedicineInfo();
    }

    public void saveAllMedicineInfo() {
        if (medicineService.existsMedicineData()) {
            log.info("Already loaded medicine data.");
            return;
        }

        String medicineInfo = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(MEDICINE_ENDPOINT)
                        .queryParam("serviceKey", MEDICINE_API_KEY)
                        .queryParam("_type", "json")
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();

        // 응답 데이터가 비어있거나 null인지 확인
        if (medicineInfo == null || medicineInfo.trim().isEmpty()) {
            System.err.println("Received empty or null response from the API.");
            return;
        }

        // 응답이 JSON으로 시작하는지 확인 (또는 필요한 경우 XML인지 확인)
        if (!medicineInfo.trim().startsWith("{")) {
            System.err.println("Received non-JSON response: " + medicineInfo);
            return;
        }

        try {
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(medicineInfo);
            JSONObject body = (JSONObject) jsonObject.get("body");

            if (body == null) {
                System.err.println("Body is missing in the JSON response.");
                return;
            }

            long totalCount = (Long) body.get("totalCount");
            long numOfRows = (Long) body.get("numOfRows");
            int totalPage = (int) Math.ceil((double) totalCount / numOfRows);

            // 이후 로직...
            for (int i = 1; i <= totalPage; i++) {
                medicineService.saveMedicineInfo(i);
            }
        } catch (ParseException e) {
            System.err.println("Failed to parse JSON response: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void saveAllPharmacyInfo() throws ParseException {
        if (pharmacyService.existsPharmacyData()) {
            log.info("Already loaded medicine data.");
            return;
        }

        String pharmacyInfo = webClient.get()
            .uri(uriBuilder -> uriBuilder
                .path(PHARMACY_ENDPOINT)
                .queryParam("serviceKey", PHARMACY_API_KEY)
                .queryParam("_type", "json")
                .build())
            .retrieve()
            .bodyToMono(String.class)
            .block();

        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(pharmacyInfo);
        JSONObject response = (JSONObject) jsonObject.get("response");
        JSONObject body = (JSONObject) response.get("body");

        int totalCount = ((Long) body.get("totalCount")).intValue();
        int numOfRows = ((Long) body.get("numOfRows")).intValue();
        int totalPage = (int) Math.ceil(totalCount / numOfRows);

        for (int i = 1; i <= totalPage; i++) {
            pharmacyService.savePharmacyInfo(i);
        }

        log.info("약국 데이터 모두 불러오기 완료");
    }

    public void saveAllHospitalInfo() throws ParseException {
        if (hospitalService.existsHospitalData()) {
            log.info("Already loaded medicine data.");
            return;
        }

        String hospitalInfo = webClient.get()
            .uri(uriBuilder -> uriBuilder
                .path(HOSPITAL_ENDPOINT)
                .queryParam("serviceKey", HOSPITAL_API_KEY)
                .queryParam("_type", "json")
                .build())
            .retrieve()
            .bodyToMono(String.class)
            .block();

        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(hospitalInfo);
        JSONObject response = (JSONObject) jsonObject.get("response");
        JSONObject body = (JSONObject) response.get("body");

        int totalCount = ((Long) body.get("totalCount")).intValue();
        int numOfRows = ((Long) body.get("numOfRows")).intValue();
        int totalPage = (int) Math.ceil(totalCount / numOfRows);

        for (int i = 1; i <= totalPage; i++) {
            hospitalService.saveHospitalInfo(i);
        }

        log.info("병원 데이터 모두 불러오기 완료");
    }
}
