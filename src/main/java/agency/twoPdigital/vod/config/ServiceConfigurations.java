package agency.twoPdigital.vod.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "services")
@ToString
@Getter
@Setter
public class ServiceConfigurations {
    private EpisodeServiceConfigurations episodeService;
}
