package dac2dac.doctect.doctor.service;

import dac2dac.doctect.agency.entity.Hospital;
import dac2dac.doctect.agency.repository.HospitalRepository;
import dac2dac.doctect.doctor.dto.DoctorDTO;
import dac2dac.doctect.doctor.entity.Department;
import dac2dac.doctect.doctor.entity.Doctor;
import dac2dac.doctect.doctor.repository.DepartmentRepository;
import dac2dac.doctect.doctor.repository.DoctorRepository;
import dac2dac.doctect.user.jwt.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DoctorLoginService {

    private final HospitalRepository hospitalRepository;
    private final DepartmentRepository departmentRepository;
    private final DoctorRepository doctorRepository;
    private final JWTUtil jwtUtil;

    public Doctor registerDoctor(DoctorDTO doctorDTO) {
        // Fetch the Hospital and Department using their IDs
        Hospital hospital = hospitalRepository.findById(doctorDTO.getHospitalId())
            .orElseThrow(() -> new IllegalArgumentException("Invalid hospital ID"));
        Department department = departmentRepository.findById(doctorDTO.getDepartmentId())
            .orElseThrow(() -> new IllegalArgumentException("Invalid department ID"));

        // Create the Doctor entity
        Doctor newDoctor = Doctor.createDoctor(
            hospital,
            department,
            doctorDTO.getName(),
            doctorDTO.getEmail(),
            doctorDTO.getPassword(),
            doctorDTO.getIsLicenseCertificated(),
            doctorDTO.getProfileImagePath(),
            doctorDTO.getOneLiner(),
            doctorDTO.getExperience(),
            doctorDTO.getDiagTime()
        );

        return doctorRepository.save(newDoctor);
    }

    public boolean authenticateUser(String username, String password) {
        Doctor doctor = doctorRepository.findByName(username);
        if (doctor != null) {
            // 비밀번호 확인
            return doctor.getPassword().equals(password); // 입력된 비밀번호와 저장된 비밀번호 비교
        }
        return false;
    }

    public Doctor findByName(String username) {
        return doctorRepository.findByName(username);
    }


    // 인증 서비스 구축
    public String authenticateAndGenerateToken(String username, String password) {
        // 사용자 인증 로직
        boolean authenticate = authenticateUser(username, password);
        Doctor doctor = doctorRepository.findByName(username);
        if (authenticate) {
            // DB에서 조회한 사용자 ID를 사용하여 JWT 생성
            return jwtUtil.DoctorcreateJwt(username, doctor.getId().toString(), doctor.getOneLiner(), doctor.getEmail(), "doctor", 36000L); // 1시간 유효한 토큰 생성
        } else {
            throw new RuntimeException("Invalid username or password");
        }
    }

}
