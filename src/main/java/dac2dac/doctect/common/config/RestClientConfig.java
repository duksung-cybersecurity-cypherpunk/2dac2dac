package dac2dac.doctect.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Value("${open-api.pharmacy.base-url}") String BASE_URL;

    @Bean
    public RestClient pharmacyRestClient() {
        RestClient restClient = RestClient.builder()
            .baseUrl(BASE_URL)
            .build();

        return restClient;
    }
}
