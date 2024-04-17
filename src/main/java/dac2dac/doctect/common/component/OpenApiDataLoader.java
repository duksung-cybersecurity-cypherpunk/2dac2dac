package dac2dac.doctect.common.component;

import dac2dac.doctect.agency.service.HospitalService;
import dac2dac.doctect.agency.service.PharmacyService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OpenApiDataLoader implements ApplicationRunner {

    private final PharmacyService pharmacyService;
    private final HospitalService hospitalService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        pharmacyService.saveAllPharmacyInfo();
        hospitalService.saveAllHospitalInfo();
    }
}
