package be.pxl.services.domain.dto;

import be.pxl.services.domain.PostStatus;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RejectedPost {
    private Long id;
    private String title;
    private String content;
    private String author;
    private LocalDate date;
    private PostStatus status;
    private String rejectionReason;
}
