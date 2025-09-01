package agency.twoPdigital.vod.config;

import agency.twoPdigital.vod.utils.QualifierKeyWord;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class HttpWebClientConfig {
    public HttpWebClientConfig(ServiceConfigurations serviceConfigurations){
        this.serviceConfigurations = serviceConfigurations;
    }
    public final  ServiceConfigurations serviceConfigurations;

    @Bean
    @Qualifier(QualifierKeyWord.EPISODE_WEB_CLIENT)
    public WebClient getEpisodeWebClient(){
        return WebClient.builder()
                .baseUrl(serviceConfigurations.getEpisodeService().getBaseUrl())
                .build();
    }

}
