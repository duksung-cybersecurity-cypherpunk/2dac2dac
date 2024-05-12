package dac2dac.doctect.mydata.repository;

import dac2dac.doctect.health_list.dto.request.DiagnosisDto;
import dac2dac.doctect.health_list.dto.request.DrugDto;
import dac2dac.doctect.health_list.dto.request.PrescriptionDto;
import dac2dac.doctect.health_list.dto.request.VaccinationDto;
import dac2dac.doctect.health_list.entity.constant.DiagType;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
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

    public Optional<Long> findByNameAndPin(String name, String pin) {
        String sql = "SELECT id FROM user WHERE name = ? AND pin = ?";
        try {
            // queryForObject가 결과를 찾으면 그 결과를 Optional로 감싸 반환
            Long userId = jdbcTemplate.queryForObject(sql, new Object[]{name, pin}, Long.class);
            return Optional.ofNullable(userId);
        } catch (EmptyResultDataAccessException e) {
            // 결과가 없을 경우 Optional.empty를 반환
            return Optional.empty();
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
}
