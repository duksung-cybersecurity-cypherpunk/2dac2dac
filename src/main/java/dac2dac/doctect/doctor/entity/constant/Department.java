package dac2dac.doctect.doctor.entity.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Department {
    FAMILY_MEDICINE("가정의학과"),
    INTERNAL_MEDICINE("내과"),
    ANESTHESIOLOGY_AND_PAIN_MEDICINE("마취통증의학과"),
    UROLOGY("비뇨의학과"),
    OBSTETRICS_AND_GYNECOLOGY("산부인과"),
    PLASTIC_SURGERY("성형외과"),
    PEDIATRICS_AND_ADOLESCENTS("소아청소년과"),
    NEUROLOGY("신경과"),
    NEUROSURGERY("신경외과"),
    OPHTHALMOLOGY("안과"),
    RADIOLOGY("영상의학과"),
    SURGERY("외과"),
    EMERGENCY_MEDICINE("응급의학과"),
    OTORHINOLARYNGOLOGY("이비인후과"),
    REHABILITATION_MEDICINE("재활의학과"),
    PSYCHIATRY("정신건강의학과"),
    ORTHOPEDICS("정형외과"),
    DENTISTRY("치의학"),
    DERMATOLOGY("피부과"),
    ORIENTAL_MEDICINE("한의학");

    private final String Department;
}
