package naver.cloud.board.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import naver.cloud.board.domain.post.BoardType;

public record PostCreateRequest(
        @NotNull BoardType boardType,
        @NotBlank String title,
        @NotBlank String content
) {
}
