package dac2dac.doctect.health_list.service;

import dac2dac.doctect.common.constant.ErrorCode;
import dac2dac.doctect.common.error.exception.NotFoundException;
import dac2dac.doctect.common.error.exception.UnauthorizedException;
import dac2dac.doctect.health_list.dto.request.DiagnosisDto;
import dac2dac.doctect.health_list.dto.request.HealthScreeningDto;
import dac2dac.doctect.health_list.dto.request.PrescriptionDto;
import dac2dac.doctect.health_list.dto.request.UserAuthenticationDto;
import dac2dac.doctect.health_list.dto.request.VaccinationDto;
import dac2dac.doctect.health_list.entity.BloodTest;
import dac2dac.doctect.health_list.entity.HealthScreening;
import dac2dac.doctect.health_list.entity.MeasurementTest;
import dac2dac.doctect.health_list.entity.OtherTest;
import dac2dac.doctect.health_list.entity.Prescription;
import dac2dac.doctect.health_list.entity.PrescriptionDrug;
import dac2dac.doctect.health_list.repository.ContactDiagRepository;
import dac2dac.doctect.health_list.repository.HealthScreeningRepository;
import dac2dac.doctect.health_list.repository.PrescriptionRepository;
import dac2dac.doctect.health_list.repository.VaccinationRepository;
import dac2dac.doctect.mydata.repository.MydataJdbcRepository;
import dac2dac.doctect.user.entity.User;
import dac2dac.doctect.user.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HealthListService {

    private final MydataJdbcRepository mydataJdbcRepository;
    private final UserRepository userRepository;
    private final ContactDiagRepository contactDiagRepository;
    private final PrescriptionRepository prescriptionRepository;
    private final HealthScreeningRepository healthScreeningRepository;
    private final VaccinationRepository vaccinationRepository;

    @Transactional
    public void syncMydata(UserAuthenticationDto userAuthenticationDto, Long userId) {
        // 유저 정보(이름, 주민등록번호)를 통해 마이데이터 DB의 유저 아이디 가져오기 (본인 인증)
        Long mydataUserId = authenticateUser(userAuthenticationDto);

        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        // 마이데이터 유저의 아이디를 이용하여 마이데이터 조회 후 Doc'tech 서버 DB에 저장한다.
        //* 진료 내역
        contactDiagRepository.deleteByUserId(userId);

        List<DiagnosisDto> diagnosisData = mydataJdbcRepository.findDiagnosisByUserId(mydataUserId);
        diagnosisData.forEach(diagnosisDto ->
            contactDiagRepository.save(diagnosisDto.toEntity(user)));

        //* 투악 내역
        prescriptionRepository.deleteByUserId(userId);

        List<PrescriptionDto> prescriptionData = mydataJdbcRepository.findPrescriptionByUserId(mydataUserId);
        prescriptionData.forEach(prescriptionDto -> {
            Prescription prescription = prescriptionDto.toEntity(user);

            prescriptionDto.getDrugDtoList().forEach(drugDto -> {
                PrescriptionDrug prescriptionDrug = drugDto.toEntity();
                prescription.addPrescriptionDrug(prescriptionDrug);
            });

            prescriptionRepository.save(prescription);
        });

        //* 건강검진 내역
        healthScreeningRepository.deleteByUserId(userId);

        List<HealthScreeningDto> healthScreeningData = mydataJdbcRepository.findHealthScreeningByUserId(mydataUserId);
        healthScreeningData.forEach(healthScreeningDto -> {
            HealthScreening healthScreening = healthScreeningDto.toEntity(user);

            MeasurementTest measurementTest = healthScreeningDto.getMeasurementTest().toEntity();
            healthScreening.setMeasurementTest(measurementTest);

            BloodTest bloodTest = healthScreeningDto.getBloodTest().toEntity();
            healthScreening.setBloodTest(bloodTest);

            OtherTest otherTest = healthScreeningDto.getOtherTest().toEntity();
            healthScreening.setOtherTest(otherTest);

            healthScreeningRepository.save(healthScreening);
        });

        //* 예방접종 내역
        vaccinationRepository.deleteByUserId(userId);

        List<VaccinationDto> vaccinationData = mydataJdbcRepository.findVaccinationByUserId(mydataUserId);
        vaccinationData.forEach(vaccinationDto ->
            vaccinationRepository.save(vaccinationDto.toEntity(user)));

    }

    private Long authenticateUser(UserAuthenticationDto userAuthenticationDto) {
        // 유저 아이디가 존재할 경우 본인 인증에 성공한 것으로 간주한다.
        return mydataJdbcRepository.findByNameAndPin(userAuthenticationDto.getName(), userAuthenticationDto.getPin())
            .orElseThrow(() -> new UnauthorizedException(ErrorCode.MYDATA_AUTHENTICATION_FAILED));
    }
}
