package dac2dac.doctect.agency.service;

import dac2dac.doctect.agency.entity.Pharmacy;
import dac2dac.doctect.agency.repository.PharmacyRepository;
import dac2dac.doctect.agency.vo.PharmacyItem;
import dac2dac.doctect.agency.vo.PharmacyItems;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class PharmacyService {

    private final WebClient webClient;
    private final PharmacyRepository pharmacyRepository;

    @Value("${open-api.pharmacy.key}")
    private String PHARMACY_API_KEY;

    @Value("${open-api.pharmacy.endpoint}")
    private String PHARMACY_ENDPOINT;

    @Async
    public void saveAllPharmacyInfo() throws ParseException {
        String pharmacyInfo = webClient.get()
            .uri(uriBuilder -> uriBuilder
                .path(PHARMACY_ENDPOINT)
                .queryParam("serviceKey", PHARMACY_API_KEY)
                .queryParam("_type", "json")
                .build())
            .retrieve()
            .bodyToMono(String.class)
            .block();

        // JSON 문자열을 JSONObject로 변환하여 totalCount 값 Parsing
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(pharmacyInfo);
        JSONObject response = (JSONObject) jsonObject.get("response");
        JSONObject body = (JSONObject) response.get("body");

        int totalCount = ((Long) body.get("totalCount")).intValue();
        int numOfRows = ((Long) body.get("numOfRows")).intValue();
        int totalPage = (int) Math.ceil(totalCount / numOfRows);

        for (int i = 1; i <= totalPage; i++) {
            int pageNo = i;

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
                    if (isValidPharmacyItem(item)) {
                        Pharmacy pharmacy = item.toEntity();
                        pharmacyRepository.save(pharmacy);
                    }
                });
            } catch (DataIntegrityViolationException e) {
            }

            System.out.println("i = " + i);
            System.out.println("pharmacyItems = " + pharmacyItems);
        }
    }

    private boolean isValidPharmacyItem(PharmacyItem item) {
        return item.getName() != null && item.getAddress() != null &&
            item.getTel() != null && item.getLongitude() != null &&
            item.getLatitude() != null;
    }
}