package agency.twoPdigital.vod.repos;

import agency.twoPdigital.vod.entities.SeasonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeasonRepo extends JpaRepository<SeasonEntity,Long> {
    public List<SeasonEntity> findByShowId(
            Long showId
    );
}
