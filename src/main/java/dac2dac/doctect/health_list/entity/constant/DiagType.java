package dac2dac.doctect.health_list.entity.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DiagType {
    GENERAL_OUTPATIENT("일반외래"),
    DENTAL_OUTPATIENT("치과외래"),
    GENERAL_HOSPITALIZATION("일반입원"),
    PRESCRIPTION_FILLING("처방조제");

    private final String DiagTypeName;
}
