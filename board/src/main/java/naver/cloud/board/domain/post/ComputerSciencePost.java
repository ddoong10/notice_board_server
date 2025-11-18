package naver.cloud.board.domain.post;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "cs_posts")
public class ComputerSciencePost extends Post {

    public ComputerSciencePost() {
        super(BoardType.DEPARTMENT_COMPUTER_SCIENCE);
    }
}
