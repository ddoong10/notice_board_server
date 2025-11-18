package naver.cloud.board.config;

import lombok.RequiredArgsConstructor;
import naver.cloud.board.domain.post.BoardType;
import naver.cloud.board.domain.post.Post;
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
                    .displayName("전전컴 홍길동")
                    .department(Department.ELECTRONICS_AND_COMPUTER_ENGINEERING)
                    .build());

            User csUser = userRepository.save(User.builder()
                    .username("csmin")
                    .password(passwordEncoder.encode("password"))
                    .displayName("컴과 이순신")
                    .department(Department.COMPUTER_SCIENCE)
                    .build());

            postRepository.save(Post.builder()
                    .boardType(BoardType.ANONYMOUS)
                    .title("익명 첫 글")
                    .content("누구든 익명으로 자유롭게 이야기해요.")
                    .author(eceUser)
                    .authorName("익명")
                    .build());

            postRepository.save(Post.builder()
                    .boardType(BoardType.DEPARTMENT_ELECTRONICS_AND_COMPUTER_ENGINEERING)
                    .title("전전컴 공지")
                    .content("전전컴 게시판 전용 공지입니다.")
                    .author(eceUser)
                    .authorName(eceUser.getDisplayName())
                    .build());

            postRepository.save(Post.builder()
                    .boardType(BoardType.DEPARTMENT_COMPUTER_SCIENCE)
                    .title("컴과 공지")
                    .content("컴과 학생만 확인하세요.")
                    .author(csUser)
                    .authorName(csUser.getDisplayName())
                    .build());

            postRepository.save(Post.builder()
                    .boardType(BoardType.NOTICE)
                    .title("전체 공지")
                    .content("모든 사용자가 확인해야 하는 공지사항입니다.")
                    .author(csUser)
                    .authorName(csUser.getDisplayName())
                    .build());
        };
    }
}
