package naver.cloud.board.domain.post;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "anonymous_posts")
public class AnonymousPost extends Post {

    public AnonymousPost() {
        super(BoardType.ANONYMOUS);
    }
}
