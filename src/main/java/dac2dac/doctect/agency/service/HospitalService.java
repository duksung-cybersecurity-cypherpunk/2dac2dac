package dac2dac.doctect.agency.service;

import dac2dac.doctect.agency.entity.Hospital;
import dac2dac.doctect.agency.repository.HospitalRepository;
import dac2dac.doctect.agency.vo.HospitalItem;
import dac2dac.doctect.agency.vo.HospitalItems;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class HospitalService {

    private final WebClient webClient;
    private final HospitalRepository hospitalRepository;

    @Value("${open-api.hospital.key}")
    private String HOSPITAL_API_KEY;

    @Value("${open-api.hospital.endpoint}")
    private String HOSPITAL_ENDPOINT;

    public void saveAllHospitalInfo() throws ParseException {
        String hospitalInfo = webClient.get()
            .uri(uriBuilder -> uriBuilder
                .path(HOSPITAL_ENDPOINT)
                .queryParam("serviceKey", HOSPITAL_API_KEY)
                .queryParam("_type", "json")
                .build())
            .retrieve()
            .bodyToMono(String.class)
            .block();

        System.out.println("hospitalInfo = " + hospitalInfo);

        // JSON 문자열을 JSONObject로 변환하여 totalCount 값 Parsing
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(hospitalInfo);
        JSONObject response = (JSONObject) jsonObject.get("response");
        JSONObject body = (JSONObject) response.get("body");

        int totalCount = ((Long) body.get("totalCount")).intValue();
        int numOfRows = ((Long) body.get("numOfRows")).intValue();
        int totalPage = totalCount / numOfRows + 1;

        for (int i=1; i<=totalPage; i++) {
            int pageNo = i;

            HospitalItems hospitalItems = webClient.get()
                .uri(uriBuilder -> uriBuilder
                    .path(HOSPITAL_ENDPOINT)
                    .queryParam("serviceKey", HOSPITAL_API_KEY)
                    .queryParam("_type", "json")
                    .queryParam("pageNo", pageNo)
                    .build())
                .retrieve()
                .bodyToMono(HospitalItems.class)
                .retry(3)
                .block();

            try {
                hospitalItems.getHospitalItems().forEach(item -> {
                    if (isValidHospitalItem(item)) {
                        Hospital hospital = item.toEntity();
                        hospitalRepository.save(hospital);
                    }
                });
            } catch (DataIntegrityViolationException e) {}

            System.out.println("i = " + i);
            System.out.println("hospitalItems = " + hospitalItems);
        }
    }

    private boolean isValidHospitalItem(HospitalItem item) {
        return item.getName() != null && item.getAddress() != null &&
            item.getTel() != null && item.getLongitude() != null &&
            item.getLatitude() != null;
    }
}
