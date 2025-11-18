package naver.cloud.board.domain.post;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "ece_posts")
public class ElectronicsAndComputerEngineeringPost extends Post {

    public ElectronicsAndComputerEngineeringPost() {
        super(BoardType.DEPARTMENT_ELECTRONICS_AND_COMPUTER_ENGINEERING);
    }
}
