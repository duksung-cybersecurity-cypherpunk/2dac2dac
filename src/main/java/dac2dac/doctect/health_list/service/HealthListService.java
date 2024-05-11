package dac2dac.doctect.health_list.service;

import dac2dac.doctect.common.constant.ErrorCode;
import dac2dac.doctect.common.error.exception.NotFoundException;
import dac2dac.doctect.common.error.exception.UnauthorizedException;
import dac2dac.doctect.health_list.dto.request.DiagnosisDto;
import dac2dac.doctect.health_list.dto.request.UserAuthenticationDto;
import dac2dac.doctect.health_list.repository.ContactDiagRepository;
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

    @Transactional
    public void syncMydata(UserAuthenticationDto userAuthenticationDto, Long userId) {
        // 유저 정보(이름, 주민등록번호)를 통해 마이데이터 DB의 유저 아이디 가져오기 (본인 인증)
        Long mydataUserId = authenticateUser(userAuthenticationDto);

        // 마이데이터 유저의 아이디를 이용하여 마이데이터 조회 후 Doc'tech 서버 DB에 저장한다.
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        //* 진료 내역 마이데이터
        // 이전 진료 내역 마이데이터 모두 삭제
        contactDiagRepository.deleteByUserId(userId);
        // 새로 조회되는 진료 내역 마이데이터 조회 후 저장
        List<DiagnosisDto> diagnosisData = mydataJdbcRepository.findDiagnosisByUserId(mydataUserId);
        diagnosisData.forEach(diagnosisDto -> contactDiagRepository.save(diagnosisDto.toEntity(user)));
    }

    private Long authenticateUser(UserAuthenticationDto userAuthenticationDto) {
        // 유저 아이디가 존재할 경우 본인 인증에 성공한 것으로 간주한다.
        return mydataJdbcRepository.findByNameAndPin(userAuthenticationDto.getName(), userAuthenticationDto.getPin())
            .orElseThrow(() -> new UnauthorizedException(ErrorCode.MYDATA_AUTHENTICATION_FAILED));
    }
}
