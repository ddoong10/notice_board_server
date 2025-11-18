package naver.cloud.board.service;

import lombok.RequiredArgsConstructor;
import naver.cloud.board.domain.post.BoardType;
import naver.cloud.board.domain.post.Post;
import naver.cloud.board.domain.post.PostFactory;
import naver.cloud.board.domain.post.PostRepository;
import naver.cloud.board.domain.user.Department;
import naver.cloud.board.domain.user.User;
import naver.cloud.board.dto.PostCreateRequest;
import naver.cloud.board.dto.PostResponse;
import naver.cloud.board.dto.PostUpdateRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional(readOnly = true)
    public List<PostResponse> getPosts(BoardType boardType) {
        return postRepository.findAllByBoardTypeOrderByCreatedAtDesc(boardType)
                .stream()
                .map(PostResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public PostResponse getPost(Long id) {
        return PostResponse.from(findPost(id));
    }

    @Transactional
    public PostResponse createPost(PostCreateRequest request, User user) {
        validateDepartmentBoard(request.boardType(), user);

        Post post = PostFactory.create(request.boardType());
        post.setTitle(request.title());
        post.setContent(request.content());
        post.setAuthor(user);
        post.setAuthorName(resolveAuthorName(request.boardType(), user));
        return PostResponse.from(postRepository.save(post));
    }

    @Transactional
    public PostResponse updatePost(Long id, PostUpdateRequest request, User user) {
        Post post = findPost(id);
        validateOwnership(post, user);
        validateDepartmentBoard(post.getBoardType(), user);

        post.setTitle(request.title());
        post.setContent(request.content());
        return PostResponse.from(post);
    }

    @Transactional
    public void deletePost(Long id, User user) {
        Post post = findPost(id);
        validateOwnership(post, user);
        postRepository.delete(post);
    }

    private Post findPost(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
    }

    private void validateDepartmentBoard(BoardType boardType, User user) {
        if (boardType == BoardType.DEPARTMENT_COMPUTER_SCIENCE && user.getDepartment() != Department.COMPUTER_SCIENCE) {
            throw new IllegalArgumentException("컴퓨터과 게시판은 컴퓨터과 학생만 글을 작성할 수 있습니다.");
        }
        if (boardType == BoardType.DEPARTMENT_ELECTRONICS_AND_COMPUTER_ENGINEERING && user.getDepartment() != Department.ELECTRONICS_AND_COMPUTER_ENGINEERING) {
            throw new IllegalArgumentException("전전컴 게시판은 전전컴 학생만 글을 작성할 수 있습니다.");
        }
    }

    private void validateOwnership(Post post, User user) {
        if (post.getAuthor() == null || !post.getAuthor().getId().equals(user.getId())) {
            throw new IllegalArgumentException("본인이 작성한 글만 수정하거나 삭제할 수 있습니다.");
        }
    }

    private String resolveAuthorName(BoardType boardType, User user) {
        if (boardType == BoardType.ANONYMOUS) {
            return "익명";
        }
        return user.getDisplayName();
    }
}
