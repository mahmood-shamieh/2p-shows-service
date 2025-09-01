package agency.twoPdigital.vod.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MediaModel {

    private Long id;

    @NotBlank(message = "Media URL is required")
    @Size(max = 2000, message = "URL must not exceed 2000 characters")
    private String url;

    @Min(value = 1, message = "Duration must be at least 1 minute")
    private int durationMinutes;

    @NotBlank(message = "Title is required")
    @Size(max = 255, message = "Title must not exceed 255 characters")
    private String title;

    @NotNull(message = "Activated flag is required")
    private Boolean activated = false;


    private EpisodeModel episode;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
