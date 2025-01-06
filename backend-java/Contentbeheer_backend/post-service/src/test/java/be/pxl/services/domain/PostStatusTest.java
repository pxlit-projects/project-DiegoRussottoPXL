package be.pxl.services.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PostStatusTest {

    @Test
    void testPostStatusEnumValues() {
        // Act
        PostStatus[] statuses = PostStatus.values();

        // Assert
        assertThat(statuses).containsExactly(PostStatus.DRAFT, PostStatus.PUBLISHED, PostStatus.PENDING);
    }

    @Test
    void testPostStatusValueOf() {
        // Act & Assert
        assertThat(PostStatus.valueOf("DRAFT")).isEqualTo(PostStatus.DRAFT);
        assertThat(PostStatus.valueOf("PUBLISHED")).isEqualTo(PostStatus.PUBLISHED);
        assertThat(PostStatus.valueOf("PENDING")).isEqualTo(PostStatus.PENDING);
    }

    @Test
    void testPostStatusEnumSize() {
        // Act
        int size = PostStatus.values().length;

        // Assert
        assertThat(size).isEqualTo(3);
    }
}
