package naver.cloud.board.api;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import naver.cloud.board.domain.user.User;
import naver.cloud.board.dto.LoginRequest;
import naver.cloud.board.dto.RegisterRequest;
import naver.cloud.board.dto.UserResponse;
import naver.cloud.board.service.BoardUserDetails;
import naver.cloud.board.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public UserResponse register(@Valid @RequestBody RegisterRequest request) {
        User user = userService.registerUser(request.username(), request.password(), request.displayName(), request.department());
        return UserResponse.from(user);
    }

    @PostMapping("/login")
    public UserResponse login(@Valid @RequestBody LoginRequest request, HttpServletRequest httpServletRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        HttpSession session = httpServletRequest.getSession(true);
        session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

        BoardUserDetails principal = (BoardUserDetails) authentication.getPrincipal();
        return UserResponse.from(principal.getUser());
    }

    @GetMapping("/me")
    public UserResponse me(Authentication authentication) {
        BoardUserDetails principal = (BoardUserDetails) authentication.getPrincipal();
        return UserResponse.from(principal.getUser());
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        SecurityContextHolder.clearContext();
    }
}
