package dac2dac.doctect.agency.service;

import dac2dac.doctect.agency.entity.Pharmacy;
import dac2dac.doctect.agency.repository.PharmacyRepository;
import dac2dac.doctect.agency.vo.PharmacyItem;
import dac2dac.doctect.agency.vo.PharmacyItems;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PharmacyService {

    private final WebClient webClient;
    private final PharmacyRepository pharmacyRepository;

    @Value("${open-api.pharmacy.key}") String PHARMACY_API_KEY;

    @Transactional
    public void saveAllPharmacyInfo() throws ParseException {
        String pharmacyInfo = webClient.get()
            .uri(uriBuilder -> uriBuilder
                .queryParam("serviceKey", PHARMACY_API_KEY)
                .queryParam("_type", "json")
                .build())
            .retrieve()
            .bodyToMono(String.class)
            .block();

        System.out.println("pharmacyInfo = " + pharmacyInfo);

        // JSON 문자열을 JSONObject로 변환하여 totalCount 값 Parsing
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(pharmacyInfo);
        JSONObject response = (JSONObject) jsonObject.get("response");
        JSONObject body = (JSONObject) response.get("body");

        int totalCount = ((Long) body.get("totalCount")).intValue();
        int numOfRows = ((Long) body.get("numOfRows")).intValue();
        int totalPage = totalCount / numOfRows + 1;

        for (int i=1; i<=totalPage; i++) {
            int pageNo = i;
            PharmacyItems pharmacies = webClient.get()
                .uri(uriBuilder -> uriBuilder
                    .queryParam("ServiceKey", PHARMACY_API_KEY)
                    .queryParam("pageNo", pageNo)
                    .queryParam("_type", "json")
                    .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(PharmacyItems.class)
                .block();

            savePharmacyInfo(pharmacies);
        }
    }

    private void savePharmacyInfo(PharmacyItems pharmacies) {
        List<PharmacyItem> items = pharmacies.getPharmacyItems();
        items.stream()
            .forEach(item -> {
                // 필수 필드(이름, 주소, 전화번호, 위도, 경도)가 존재하는지 확인 후 DB에 저장
                if (item.getName() != null && item.getAddress() != null && item.getTel() != null && item.getLongitude() != null && item.getLatitude() != null) {
                    Pharmacy pharmacy = item.toEntity();
                    pharmacyRepository.save(pharmacy);
                }
        });
    }
}
