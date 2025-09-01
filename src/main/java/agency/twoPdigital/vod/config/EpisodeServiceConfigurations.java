package agency.twoPdigital.vod.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EpisodeServiceConfigurations extends SystemInstance{
    private String getAllEpisodeBySeasonId;
}
