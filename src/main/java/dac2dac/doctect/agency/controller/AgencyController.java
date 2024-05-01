package dac2dac.doctect.agency.controller;

import dac2dac.doctect.agency.dto.request.UserLocationDto;
import dac2dac.doctect.agency.service.AgencyService;
import dac2dac.doctect.agency.service.HospitalService;
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

@Tag(name = "병원&약국 기관", description = "Agency API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/agency")
public class AgencyController {

    private final PharmacyService pharmacyService;
    private final HospitalService hospitalService;
    private final AgencyService agencyService;

    @Operation(summary = "test hello", description = "hello api example")
    @GetMapping("/search")
    public ApiResult searchAgency(@RequestBody UserLocationDto userLocationDto) throws ParseException {
        return ApiResult.success(SuccessCode.GET_SUCCESS, agencyService.searchAgency(userLocationDto));
    }


}
