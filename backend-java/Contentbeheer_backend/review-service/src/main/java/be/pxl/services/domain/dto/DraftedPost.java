package be.pxl.services.domain.dto;

import be.pxl.services.domain.PostStatus;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class DraftedPost {
    private long id;
    private String title;
    private String content;
    private String author;
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private PostStatus status; // Zorg ervoor dat PostStatus een enum is
}
