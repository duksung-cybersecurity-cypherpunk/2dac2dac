package dac2dac.doctect.agency.controller;

import dac2dac.doctect.agency.dto.request.UserLocationDto;
import dac2dac.doctect.agency.service.PharmacyService;
import dac2dac.doctect.common.constant.SuccessCode;
import dac2dac.doctect.common.response.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.io.ParseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "약국", description = "Pharmacy API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PharmacyController {

    private final PharmacyService pharmacyService;

    @Operation(summary = "test hello", description = "hello api example")
    @GetMapping("/search/pharmacy")
    public ApiResult getSearchPharmacy(@RequestBody UserLocationDto userLocationDto) throws ParseException {
        return ApiResult.success(SuccessCode.GET_SUCCESS, pharmacyService.getNearbyPharmacy(userLocationDto));
    }

}
