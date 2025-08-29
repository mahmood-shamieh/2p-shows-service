package agency.twoPdigital.vod.models;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;


@Builder
@Setter
@Getter
public class SeasonModel {

    private Long id;

    @NotNull(message = "Season is mandatory")
    private Integer seasonNumber;

    @NotBlank(message = "Title is mandatory")
    @Size(max = 100, message = "Title cannot exceed 100 characters")
    private String title;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    @NotNull(message = "ShowId is mandatory")
    private Long showId;

    @NotNull(message = "Activated flag is required")
    private Boolean activated ;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
