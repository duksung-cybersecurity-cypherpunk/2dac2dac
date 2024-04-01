package dac2dac.doctect.agency.service;

import dac2dac.doctect.agency.entity.Pharmacy;
import dac2dac.doctect.agency.repository.PharmacyRepository;
import dac2dac.doctect.agency.vo.PharmacyItem;
import dac2dac.doctect.agency.vo.PharmacyItems;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
    public PharmacyItems getAllPharmacyInfo() {
        PharmacyItems pharmacyInfo = webClient.get()
            .uri(uriBuilder -> uriBuilder
                .queryParam("serviceKey", PHARMACY_API_KEY)
                .queryParam("_type", "json")
                .build())
            .retrieve()
            .bodyToMono(PharmacyItems.class)
            .block();

        List<PharmacyItem> items = pharmacyInfo.getPharmacyItems();
        items.stream().forEach(item -> {
            Pharmacy pharmacy = item.toEntity();
            pharmacyRepository.save(pharmacy);
        });

        return pharmacyInfo;
    }
}
