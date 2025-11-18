package naver.cloud.board.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import naver.cloud.board.domain.post.BoardType;
import naver.cloud.board.domain.user.User;
import naver.cloud.board.dto.PostCreateRequest;
import naver.cloud.board.dto.PostResponse;
import naver.cloud.board.dto.PostUpdateRequest;
import naver.cloud.board.service.BoardUserDetails;
import naver.cloud.board.service.PostService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    public List<PostResponse> getPosts(@RequestParam BoardType boardType) {
        return postService.getPosts(boardType);
    }

    @GetMapping("/{id}")
    public PostResponse getPost(@PathVariable Long id) {
        return postService.getPost(id);
    }

    @PostMapping
    public PostResponse createPost(@Valid @RequestBody PostCreateRequest request,
                                   @AuthenticationPrincipal BoardUserDetails userDetails) {
        User user = userDetails.getUser();
        return postService.createPost(request, user);
    }

    @PutMapping("/{id}")
    public PostResponse updatePost(@PathVariable Long id,
                                   @Valid @RequestBody PostUpdateRequest request,
                                   @AuthenticationPrincipal BoardUserDetails userDetails) {
        User user = userDetails.getUser();
        return postService.updatePost(id, request, user);
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Long id, @AuthenticationPrincipal BoardUserDetails userDetails) {
        User user = userDetails.getUser();
        postService.deletePost(id, user);
    }
}
