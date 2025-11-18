package naver.cloud.board.domain.post;

public final class PostFactory {

    private PostFactory() {
    }

    public static Post create(BoardType boardType) {
        return switch (boardType) {
            case ANONYMOUS -> new AnonymousPost();
            case DEPARTMENT_ELECTRONICS_AND_COMPUTER_ENGINEERING -> new ElectronicsAndComputerEngineeringPost();
            case DEPARTMENT_COMPUTER_SCIENCE -> new ComputerSciencePost();
            case NOTICE -> new NoticePost();
        };
    }
}
