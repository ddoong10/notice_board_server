package naver.cloud.board.config;

import lombok.RequiredArgsConstructor;
import naver.cloud.board.domain.post.BoardType;
import naver.cloud.board.domain.post.Post;
import naver.cloud.board.domain.post.PostFactory;
import naver.cloud.board.domain.post.PostRepository;
import naver.cloud.board.domain.user.Department;
import naver.cloud.board.domain.user.User;
import naver.cloud.board.domain.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner seedData(UserRepository userRepository, PostRepository postRepository) {
        return args -> {
            if (userRepository.count() > 0) {
                return;
            }

            User eceUser = userRepository.save(User.builder()
                    .username("ecejun")
                    .password(passwordEncoder.encode("password"))
                    .displayName("전전컴 공지사항입니다.")
                    .department(Department.ELECTRONICS_AND_COMPUTER_ENGINEERING)
                    .build());

            User csUser = userRepository.save(User.builder()
                    .username("csmin")
                    .password(passwordEncoder.encode("password"))
                    .displayName("컴퓨터과학부 공지사항입니다.")
                    .department(Department.COMPUTER_SCIENCE)
                    .build());

            postRepository.save(createSeedPost(BoardType.ANONYMOUS, "익명 첫 글",
                    "환영합니다. 익명 게시판의 첫 글입니다.", eceUser, "익명"));

            postRepository.save(createSeedPost(BoardType.DEPARTMENT_ELECTRONICS_AND_COMPUTER_ENGINEERING,
                    "전전컴 공지", "전전컴 게시판 공지입니다.", eceUser, eceUser.getDisplayName()));

            postRepository.save(createSeedPost(BoardType.DEPARTMENT_COMPUTER_SCIENCE,
                    "컴과 공지", "컴과 학생들은 꼭 확인해주세요.", csUser, csUser.getDisplayName()));

            postRepository.save(createSeedPost(BoardType.NOTICE,
                    "전체 공지", "모든 사용자가 확인해야 하는 공지입니다.", csUser, csUser.getDisplayName()));
        };
    }

    private Post createSeedPost(BoardType boardType, String title, String content, User author, String authorName) {
        Post post = PostFactory.create(boardType);
        post.setTitle(title);
        post.setContent(content);
        post.setAuthor(author);
        post.setAuthorName(authorName);
        return post;
    }
}
