package com.qring.review.domain.model;

import io.hypersistence.utils.hibernate.id.Tsid;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_review")
public class ReviewEntity {

    @Id @Tsid
    @Column(name = "review_id")
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "restaurant_Id", nullable = false)
    private Long restaurantId;

    @Column(name = "rating", nullable = false)
    private int rating;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "rating_average")
    private float ratingAverage;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "modified_at" , nullable = false)
    private LocalDateTime modifiedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "created_by", updatable = false)
    private String createdBy;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "deleted_by")
    private String deletedBy;

    @Builder
    public ReviewEntity (Long userId, Long restaurantId, int rating, String content) {
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.rating = rating;
        this.content = content;
    }
}
