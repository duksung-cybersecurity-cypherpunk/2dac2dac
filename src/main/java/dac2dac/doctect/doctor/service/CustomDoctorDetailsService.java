package dac2dac.doctect.doctor.service;

import dac2dac.doctect.doctor.dto.CustomDoctorDetails;
import dac2dac.doctect.doctor.entity.Doctor;
import dac2dac.doctect.doctor.repository.DoctorRepository;

import dac2dac.doctect.user.dto.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomDoctorDetailsService implements UserDetailsService {

    private final DoctorRepository doctorRepository; // 세미콜론 추가

    @Autowired
    public CustomDoctorDetailsService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Doctor doctor = doctorRepository.findByName(username);
        if (doctor != null) {
            return new CustomDoctorDetails(doctor);
        }
        // 사용자 정보를 찾지 못한 경우 UsernameNotFoundException을 던집니다.
        throw new UsernameNotFoundException("User not found with username: " + username);
    }
}
