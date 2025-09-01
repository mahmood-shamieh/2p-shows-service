package agency.twoPdigital.vod.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EpisodeModel {
    private Long id;

    @Min(value = 1, message = "Episode number must be at least 1")
    private int episodeNumber;

    @Min(value = 1, message = "Season number must be at least 1")
    private Long seasonId;

    @NotBlank(message = "Title is required")
    @Size(max = 255, message = "Title must not exceed 255 characters")
    private String title;

    @NotBlank(message = "Description is required")
    @Size(max = 2000, message = "Description must not exceed 2000 characters")
    private String description;

    private List<MediaModel> mediaList;

    @NotNull(message = "Activated flag is required")
    private Boolean activated = false;


    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
