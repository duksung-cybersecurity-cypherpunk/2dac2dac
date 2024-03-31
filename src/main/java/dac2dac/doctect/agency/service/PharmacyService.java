package dac2dac.doctect.agency.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import dac2dac.doctect.agency.entity.Pharmacy;
import dac2dac.doctect.agency.repository.PharmacyRepository;
import dac2dac.doctect.agency.vo.PharmacyItems;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PharmacyService {

    private final RestClient restClient;
    private final PharmacyRepository pharmacyRepository;

    @Value("${open-api.pharmacy.api-key}") String PHARMACY_API_KEY;

    @Transactional
    public Object getAllPharmacyInfo() {
        String pharmacyInfo = restClient.get()
            .uri(uriBuilder -> uriBuilder
                .queryParam("serviceKey", PHARMACY_API_KEY)
                .queryParam("_type", "json")
                .build())
            .retrieve()
            .body(String.class);

        PharmacyItems items = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            items = mapper.readValue(pharmacyInfo, PharmacyItems.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        items.getPharmacyItems().stream().forEach(item -> {
            Pharmacy pharmacy = item.toEntity();
            System.out.println("pharmacy = " + pharmacy);
            pharmacyRepository.save(pharmacy);
        });

        return items;
    }
}
