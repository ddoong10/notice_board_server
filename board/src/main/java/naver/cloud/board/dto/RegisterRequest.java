package naver.cloud.board.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import naver.cloud.board.domain.user.Department;

public record RegisterRequest(
        @NotBlank String username,
        @NotBlank String password,
        @NotBlank String displayName,
        @NotNull Department department
) {
}
