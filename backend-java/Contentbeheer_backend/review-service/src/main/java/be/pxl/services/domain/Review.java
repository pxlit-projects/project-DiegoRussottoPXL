package be.pxl.services.domain;

import be.pxl.services.domain.dto.DraftedPost;
import be.pxl.services.domain.dto.PostResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name="review")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ElementCollection
    @CollectionTable(name = "drafted_posts", joinColumns = @JoinColumn(name = "review_id"))
    private List<DraftedPost> draftedPosts;
}
