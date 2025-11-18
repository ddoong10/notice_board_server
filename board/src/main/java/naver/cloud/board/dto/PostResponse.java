package naver.cloud.board.dto;

import naver.cloud.board.domain.post.BoardType;
import naver.cloud.board.domain.post.Post;

import java.time.LocalDateTime;

public record PostResponse(
        Long id,
        BoardType boardType,
        String title,
        String content,
        String authorName,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static PostResponse from(Post post) {
        return new PostResponse(
                post.getId(),
                post.getBoardType(),
                post.getTitle(),
                post.getContent(),
                post.getAuthorName(),
                post.getCreatedAt(),
                post.getUpdatedAt()
        );
    }
}
