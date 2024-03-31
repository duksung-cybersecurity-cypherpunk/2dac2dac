package dac2dac.doctect.agency.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import dac2dac.doctect.agency.dto.PharmacyInfoDto;
import dac2dac.doctect.agency.service.PharmacyService;
import dac2dac.doctect.common.constant.SuccessCode;
import dac2dac.doctect.common.response.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "약국", description = "Pharmacy API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/pharmacy")
public class PharmacyController {

    private final PharmacyService pharmacyService;

    @Value("${open-api.pharmacy.api-key}") String PHARMACY_API_KEY;

    @Operation(summary = "pharmacy", description = "hello api example")
    @GetMapping("/")
    public Object getAllPharmacyInfo() {
        return ApiResult.success(SuccessCode.GET_SUCCESS, pharmacyService.getAllPharmacyInfo());
    }
}
