package dac2dac.doctect.mydata.repository;

import dac2dac.doctect.health_list.dto.request.BloodTestDto;
import dac2dac.doctect.health_list.dto.request.DiagnosisDto;
import dac2dac.doctect.health_list.dto.request.DrugDto;
import dac2dac.doctect.health_list.dto.request.HealthScreeningDto;
import dac2dac.doctect.health_list.dto.request.MeasurementTestDto;
import dac2dac.doctect.health_list.dto.request.OtherTestDto;
import dac2dac.doctect.health_list.dto.request.PrescriptionDto;
import dac2dac.doctect.health_list.dto.request.VaccinationDto;
import dac2dac.doctect.health_list.entity.constant.DiagType;
import dac2dac.doctect.health_list.entity.constant.healthScreening.TBChestDisease;
import dac2dac.doctect.health_list.entity.constant.healthScreening.UrinaryProtein;
import dac2dac.doctect.health_list.entity.constant.healthScreening.boneDensityTest.BoneDensityTest;
import dac2dac.doctect.health_list.entity.constant.healthScreening.cognitiveDysfunction.CognitiveDysfunction;
import dac2dac.doctect.health_list.entity.constant.healthScreening.depression.Depression;
import dac2dac.doctect.health_list.entity.constant.healthScreening.elderlyFunctionalAssessment.ElderlyFunctionalAssessmentADL;
import dac2dac.doctect.health_list.entity.constant.healthScreening.elderlyFunctionalAssessment.ElderlyFunctionalAssessmentFalls;
import dac2dac.doctect.health_list.entity.constant.healthScreening.elderlyFunctionalAssessment.ElderlyFunctionalAssessmentUrinaryIncontinence;
import dac2dac.doctect.health_list.entity.constant.healthScreening.elderlyFunctionalAssessment.ElderlyFunctionalAssessmentVaccination;
import dac2dac.doctect.health_list.entity.constant.healthScreening.elderlyPhysicalFunctionTest.ElderlyPhysicalFunctionTest;
import dac2dac.doctect.health_list.entity.constant.healthScreening.hepB.HepB;
import dac2dac.doctect.health_list.entity.constant.healthScreening.hepB.HepBSurfaceAntibody;
import dac2dac.doctect.health_list.entity.constant.healthScreening.hepB.HepBSurfaceAntigen;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MydataJdbcRepository {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public MydataJdbcRepository(@Qualifier("secondaryDataSource") DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public Optional<Map<String, Object>> findByNameAndPin(String name, String pin) {
        String sql = "SELECT id, name, pin FROM user WHERE name = ? AND pin = ?";

        try {
            Map<String, Object> userData = jdbcTemplate.queryForObject(sql, new Object[]{name, pin}, new UserRowMapper());
            return Optional.ofNullable(userData);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private static class UserRowMapper implements RowMapper<Map<String, Object>> {
        @Override
        public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
            Map<String, Object> userData = new HashMap<>();
            userData.put("id", rs.getLong("id"));
            userData.put("name", rs.getString("name"));
            userData.put("pin", rs.getString("pin"));
            return userData;
        }
    }

    public List<DiagnosisDto> findDiagnosisByUserId(Long userId) {
        String sql = "SELECT * FROM diagnosis d WHERE d.user_id = :userId";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("userId", userId);

        return namedParameterJdbcTemplate.query(sql, parameters, (rs, rowNum) -> {
            return DiagnosisDto.builder()
                .agencyName(rs.getString("pharm_nm"))
                .diagDate(rs.getTimestamp("diag_date").toLocalDateTime())
                .diagType(DiagType.fromString(rs.getString("diag_type")))
                .prescription_cnt(rs.getInt("res_prescribe_cnt"))
                .medication_cnt(rs.getInt("res_medication_cnt"))
                .visit_days(rs.getInt("res_visit_days"))
                .build();
        });
    }

    public List<PrescriptionDto> findPrescriptionByUserId(Long userId) {
        String sql = "SELECT * FROM prescription p WHERE p.user_id = :userId";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("userId", userId);

        return namedParameterJdbcTemplate.query(sql, parameters, (rs, rowNum) -> {
            return PrescriptionDto.builder()
                .treatDate(rs.getTimestamp("res_treat_date").toLocalDateTime())
                .agencyName(rs.getString("res_hospital_name"))
                .drugDtoList(findDrugByPrescriptionId(rs.getLong("id")))
                .build();
        });
    }

    public List<DrugDto> findDrugByPrescriptionId(Long prescriptionId) {
        String sql = "SELECT * FROM prescription_drug pd WHERE pd.prescription_id = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", prescriptionId);

        return namedParameterJdbcTemplate.query(sql, parameters, (rs, rowNum) -> {
            return DrugDto.builder()
                .drugName(rs.getString("res_prescribe_drug_name"))
                .prescriptionCnt(rs.getInt("res_prescribe_cnt_det"))
                .medicationDays(rs.getInt("res_prescribe_days"))
                .build();
        });
    }

    public List<VaccinationDto> findVaccinationByUserId(Long userId) {
        String sql = "SELECT * FROM vaccination v WHERE v.user_id = :userId";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("userId", userId);

        return namedParameterJdbcTemplate.query(sql, parameters, (rs, rowNum) -> {
            return VaccinationDto.builder()
                .agencyName(rs.getString("provider"))
                .vaccine(rs.getString("vaccine"))
                .vaccSeries(rs.getInt("vacc_series"))
                .vaccDate(rs.getTimestamp("given_date").toLocalDateTime())
                .build();
        });
    }

    public List<HealthScreeningDto> findHealthScreeningByUserId(Long userId) {
        String sql = "SELECT * FROM health_screening h WHERE h.user_id = :userId";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("userId", userId);

        return namedParameterJdbcTemplate.query(sql, parameters, (rs, rowNum) -> {
            return HealthScreeningDto.builder()
                .measurementTest(findMeasurementTestById(rs.getLong("measurement_test_id")))
                .bloodTest(findBloodTestById(rs.getLong("blood_test_id")))
                .otherTest(findOtherTestById(rs.getLong("other_test_id")))
                .agencyName(rs.getString("res_organization_name"))
                .doctorName(rs.getString("res_doctor_name"))
                .checkupDate(rs.getTimestamp("res_checkup_date").toLocalDateTime())
                .opinion(rs.getString("res_opinion"))
                .build();
        });
    }

    private OtherTestDto findOtherTestById(Long otherTestId) {
        String sql = "SELECT * FROM other_test o WHERE o.id = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", otherTestId);

        try {
            return namedParameterJdbcTemplate.queryForObject(sql, parameters, this::mapRowToOtherTestDto);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private BloodTestDto findBloodTestById(Long bloodTestId) {
        String sql = "SELECT * FROM blood_test b WHERE b.id = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", bloodTestId);

        try {
            return namedParameterJdbcTemplate.queryForObject(sql, parameters, this::mapRowToBloodTestDto);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private MeasurementTestDto findMeasurementTestById(Long measurementTestId) {
        String sql = "SELECT * FROM measurement_test m WHERE m.id = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("id", measurementTestId);

        try {
            return namedParameterJdbcTemplate.queryForObject(sql, parameters, this::mapRowToMeasurementTestDto);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private MeasurementTestDto mapRowToMeasurementTestDto(ResultSet rs, int rowNum) throws SQLException {
        return MeasurementTestDto.builder()
            .height(rs.getDouble("res_height"))
            .weight(rs.getDouble("res_weight"))
            .waist(rs.getDouble("res_waist"))
            .bmi(rs.getDouble("res_bmi"))
            .sightLeft(rs.getDouble("res_sight_left"))
            .sightRight(rs.getDouble("res_sight_right"))
            .hearingLeft(rs.getInt("res_hearing_left"))
            .hearingRight(rs.getInt("res_hearing_right"))
            .bloodPressureHigh(rs.getInt("res_blood_pressure_high"))
            .bloodPressureLow(rs.getInt("res_blood_pressure_low"))
            .build();
    }

    private BloodTestDto mapRowToBloodTestDto(ResultSet rs, int rowNum) throws SQLException {
        return BloodTestDto.builder()
            .hemoglobin(rs.getDouble("res_hemoglobin"))
            .fastingBloodSugar(rs.getDouble("res_fasting_blood_suger"))
            .totalCholesterol(rs.getDouble("res_total_cholesterol"))
            .HDLCholesterol(rs.getDouble("res_hdl_cholesterol"))
            .triglyceride(rs.getDouble("res_triglyceride"))
            .LDLCholesterol(rs.getDouble("res_ldl_cholesterol"))
            .serumCreatinine(rs.getDouble("res_serum_creatinine"))
            .GFR(rs.getInt("res_gfr"))
            .AST(rs.getInt("res_ast"))
            .ALT(rs.getInt("res_alt"))
            .GPT(rs.getInt("resy_gpt"))
            .build();
    }

    private OtherTestDto mapRowToOtherTestDto(ResultSet rs, int rowNum) throws SQLException {
        return OtherTestDto.builder()
            .urinaryProtein(UrinaryProtein.fromString(rs.getString("res_urinary_protein")))
            .TBChestDisease(TBChestDisease.fromString(rs.getString("res_tb_chest_disease")))
            .isHepB(rs.getBoolean("is_hep_b"))
            .hepBSurfaceAntibody(HepBSurfaceAntibody.fromString(rs.getString("hep_b_surface_antibody")))
            .hepBSurfaceAntigen(HepBSurfaceAntigen.fromString(rs.getString("hep_b_surface_antigen")))
            .hepB(HepB.fromString(rs.getString("hep_b")))
            .isDepression(rs.getBoolean("is_depression"))
            .depression(Depression.fromString(rs.getString("depression")))
            .isCognitiveDysfunction(rs.getBoolean("is_cognitive_dysfunction"))
            .cognitiveDysfunction(CognitiveDysfunction.fromString(rs.getString("cognitive_dysfunction")))
            .isBoneDensityTest(rs.getBoolean("is_bone_density_test"))
            .boneDensityTest(BoneDensityTest.fromString(rs.getString("bone_density_test")))
            .isElderlyPhysicalFunctionTest(rs.getBoolean("is_elderly_physical_function_test"))
            .elderlyPhysicalFunctionTest(ElderlyPhysicalFunctionTest.fromString(rs.getString("elderly_physical_function_test")))
            .isElderlyFunctionalAssessment(rs.getBoolean("is_elderly_functional_assessment"))
            .elderlyFunctionalAssessmentFalls(ElderlyFunctionalAssessmentFalls.fromString(rs.getString("elderly_functional_assessment_falls")))
            .elderlyFunctionalAssessmentADL(ElderlyFunctionalAssessmentADL.fromString(rs.getString("elderly_functional_assessment_adl")))
            .elderlyFunctionalAssessmentVaccination(ElderlyFunctionalAssessmentVaccination.fromString(rs.getString("elderly_functional_assessment_vaccination")))
            .elderlyFunctionalAssessmentUrinaryIncontinence(ElderlyFunctionalAssessmentUrinaryIncontinence.fromString(rs.getString("elderly_functional_assessment_urinary_incontinence")))
            .smoke(rs.getBoolean("medical_interview_smoke"))
            .drink(rs.getBoolean("medical_interview_drink"))
            .physicalActivity(rs.getBoolean("medical_interview_physical_activity"))
            .exercise(rs.getBoolean("medical_interview_exercise"))
            .build();
    }
}
