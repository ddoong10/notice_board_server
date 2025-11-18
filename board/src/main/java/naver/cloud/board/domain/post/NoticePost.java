package naver.cloud.board.domain.post;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "notice_posts")
public class NoticePost extends Post {

    public NoticePost() {
        super(BoardType.NOTICE);
    }
}
