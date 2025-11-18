package naver.cloud.board.dto;

import naver.cloud.board.domain.user.Department;
import naver.cloud.board.domain.user.User;

public record UserResponse(
        Long id,
        String username,
        String displayName,
        Department department
) {
    public static UserResponse from(User user) {
        return new UserResponse(user.getId(), user.getUsername(), user.getDisplayName(), user.getDepartment());
    }
}
