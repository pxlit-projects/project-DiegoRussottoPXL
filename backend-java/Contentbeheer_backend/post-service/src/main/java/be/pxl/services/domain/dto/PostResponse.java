package be.pxl.services.domain.dto;

import be.pxl.services.domain.PostStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse {
    private long id;
    private String title;
    private String content;
    private String author;
    private LocalDate date;
    private PostStatus status;

}
