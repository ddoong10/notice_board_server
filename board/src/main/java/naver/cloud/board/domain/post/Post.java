package naver.cloud.board.domain.post;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import naver.cloud.board.domain.user.User;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Post {

    @Id
    @TableGenerator(
            name = "post_id_generator",
            table = "post_id_sequences",
            pkColumnName = "seq_name",
            valueColumnName = "seq_value",
            pkColumnValue = "post_id",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "post_id_generator")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 80, updatable = false)
    private BoardType boardType;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false, length = 50)
    private String authorName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User author;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    protected Post(BoardType boardType) {
        this.boardType = boardType;
    }
}
