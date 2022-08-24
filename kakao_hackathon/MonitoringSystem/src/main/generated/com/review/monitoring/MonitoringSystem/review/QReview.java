package com.review.monitoring.MonitoringSystem.review;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QReview is a Querydsl query type for Review
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReview extends EntityPathBase<Review> {

    private static final long serialVersionUID = 318104052L;

    public static final QReview review = new QReview("review");

    public final StringPath comment = createString("comment");

    public final DateTimePath<java.time.LocalDateTime> date = createDateTime("date", java.time.LocalDateTime.class);

    public final StringPath department = createString("department");

    public final NumberPath<Integer> feedback = createNumber("feedback", Integer.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath mu_keyword = createString("mu_keyword");

    public final NumberPath<Integer> score = createNumber("score", Integer.class);

    public final NumberPath<Integer> star = createNumber("star", Integer.class);

    public QReview(String variable) {
        super(Review.class, forVariable(variable));
    }

    public QReview(Path<? extends Review> path) {
        super(path.getType(), path.getMetadata());
    }

    public QReview(PathMetadata metadata) {
        super(Review.class, metadata);
    }

}

