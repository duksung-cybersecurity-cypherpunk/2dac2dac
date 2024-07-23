package dac2dac.doctect.noncontact_diag.dto.request;

import dac2dac.doctect.noncontact_diag.entity.constant.NoncontactDiagType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NoncontactDiagAppointmentRequestDto {

    //* 증상
    @Size(min = 5, max = 255, message = "복용중인 약 정보는 5자 이상, 255자 이하로 입력해 주세요.")
    private String prescribedDrug;
    @NotNull(message = "복용중인 약 여부는 필수 입력 항목입니다.")
    private Boolean isPrescribedDrug;

    @Size(min = 5, max = 255, message = "알레르기 증상 정보는 5자 이상, 255자 이하로 입력해 주세요.")
    private String allergicSymptom;
    @NotNull(message = "알레르기 증상 여부는 필수 입력 항목입니다.")
    private Boolean isAllergicSymptom;

    @Size(min = 5, max = 255, message = "선척적 질환 정보는 5자 이상, 255자 이하로 입력해 주세요.")
    private String inbornDisease;
    @NotNull(message = "선천적 질환 여부는 필수 입력 항목입니다.")
    private Boolean isInbornDisease;

    @Size(min = 5, max = 255, message = "기타 정보는 5자 이상, 255자 이하로 입력해 주세요.")
    private String additionalInformation;

    //* 진료 예약 날짜
    @NotNull(message = "진료 예약 날짜는 필수 입력 항목입니다.")
    private LocalDate reservationDate;
    @NotNull(message = "진료 예약 시간은 필수 입력 항목입니다.")
    @Size(min = 4, max = 4, message = "진료 예약 시간은 4자리 숫자로 입력해 주세요.")
    private String reservationTime;

    //* 진료 방식
    @NotNull(message = "진료 방식은 필수 입력 항목입니다.")
    private NoncontactDiagType diagType;

    @NotNull(message = "빌링키는 필수 입력 항목입니다.")
    private String billingKey;

    //* 약관 동의 여부
    @NotNull(message = "약관 동의 여부는 필수 입력 항목입니다.")
    private Boolean isConsent;

    @NotNull(message = "의사 ID는 필수 입력 항목입니다.")
    private Long doctorId;
}
