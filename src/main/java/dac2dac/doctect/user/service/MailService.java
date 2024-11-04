package dac2dac.doctect.user.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
@RequiredArgsConstructor
public class MailService {

    private static final Logger LOGGER = Logger.getLogger(MailService.class.getName());

    private final JavaMailSender emailSender;
    private final SpringTemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String fromEmail;

    private final Map<String, String> emailAuthMap = new HashMap<>();

    // 랜덤 인증 코드 생성
    private String createCode() {
        LOGGER.log(Level.INFO, "인증 코드 생성 시작.");
        Random random = new Random();
        StringBuilder key = new StringBuilder();

        for (int i = 0; i < 8; i++) {
            int index = random.nextInt(3);

            switch (index) {
                case 0 -> key.append((char) (random.nextInt(26) + 97)); // 소문자
                case 1 -> key.append((char) (random.nextInt(26) + 65)); // 대문자
                case 2 -> key.append(random.nextInt(10)); // 숫자
            }
        }
        String authCode = key.toString();
        LOGGER.log(Level.INFO, "생성된 인증 코드: {0}", authCode);
        return authCode;
    }

    // 메일 양식 작성
    public MimeMessage createEmailForm(String email) throws MessagingException, UnsupportedEncodingException {
        String authNum = createCode(); // 인증 코드 생성
        String title = "Doc'Tech 인증 번호"; // 이메일 제목

        LOGGER.log(Level.INFO, "이메일 양식 작성 중: 받는 사람 {0}, 인증 코드: {1}", new Object[]{email, authNum});

        MimeMessage message = emailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, email); // 받는 사람의 이메일 주소 설정
        message.setSubject(title); // 제목 설정
        message.setFrom(fromEmail); // 보내는 사람의 이메일 주소 설정
        message.setContent(setContext(authNum), "text/html; charset=utf-8");

        emailAuthMap.put(email, authNum); // 이메일과 인증 코드를 매핑하여 저장
        LOGGER.log(Level.INFO, "이메일 양식 작성 완료. 이메일: {0}, 인증 코드: {1}", new Object[]{email, authNum});

        return message;
    }

    // 실제 메일 전송
    public String sendEmail(String toEmail) throws MessagingException, UnsupportedEncodingException {
        LOGGER.log(Level.INFO, "이메일 전송 시작: {0}", toEmail);
        MimeMessage emailForm = createEmailForm(toEmail); // 메일 양식 작성
        emailSender.send(emailForm); // 메일 전송
        LOGGER.log(Level.INFO, "이메일 전송 완료: {0}", toEmail);
        return emailAuthMap.get(toEmail); // 전송된 인증 코드를 반환
    }

    // 인증 코드 검증
    public boolean verifyAuthCode(String email, String authCode) {
        LOGGER.log(Level.INFO, "인증 코드 검증 시작. 이메일: {0}, 입력된 코드: {1}", new Object[]{email, authCode});
        String storedCode = emailAuthMap.get(email);
        boolean result = storedCode != null && storedCode.equals(authCode);
        LOGGER.log(Level.INFO, "인증 코드 검증 결과: {0}", result ? "성공" : "실패");
        return result;
    }

    // Thymeleaf를 이용한 context 설정
    public String setContext(String code) {
        Context context = new Context();
        context.setVariable("code", code);
        LOGGER.log(Level.INFO, "Thymeleaf 템플릿 설정 완료. 인증 코드: {0}", code);
        return templateEngine.process("mail", context); // mail.html
    }
}