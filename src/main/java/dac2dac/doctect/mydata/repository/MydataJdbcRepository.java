package dac2dac.doctect.mydata.repository;

import dac2dac.doctect.health_list.dto.request.DiagnosisDto;
import dac2dac.doctect.health_list.entity.constant.DiagType;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
    public MydataJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    public Long findByNameAndPin(String name, String pin) {
        String sql = "SELECT id FROM user WHERE name = ? AND pin = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{name, pin}, Long.class);
        } catch (EmptyResultDataAccessException e) {
            // 유저 아이디가 존재하지 않을 경우 null을 반환
            return null;
        }
    }

    public List<DiagnosisDto> findDiagnosisByUserIdAsTuple(Long userId) {
//        String sql = "SELECT d.pharm_nm as agencyName, d.diag_date as diagDate, d.diag_type as diagType, d.res_prescribe_cnt as prescriptionCnt, d.res_medication_cnt as medicationCnt, d.res_visit_days as visitDays FROM diagnosis d WHERE d.user_id = :userId";
        String sql = "SELECT * FROM diagnosis d WHERE d.user_id = :userId";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("userId", userId);

        return namedParameterJdbcTemplate.query(sql, parameters, (rs, rowNum) -> {
            return DiagnosisDto.builder()
                .agencyName(rs.getString("pharm_nm"))
                .diagDate(rs.getTimestamp("diag_date").toLocalDateTime())
                .diagType(DiagType.valueOf(rs.getString("diag_type")))
                .prescription_cnt(rs.getInt("res_prescribe_cnt"))
                .medication_cnt(rs.getInt("res_medication_cnt"))
                .visit_days(rs.getInt("res_visit_days"))
                .build();
        });
    }

}
