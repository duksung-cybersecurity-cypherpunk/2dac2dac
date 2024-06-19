package dac2dac.doctect.review.repository;

import dac2dac.doctect.review.entity.ReviewReviewTag;
import dac2dac.doctect.review.entity.ReviewTag;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewReviewTagRepository extends JpaRepository<ReviewReviewTag, Long> {

    @Query("select rrt.reviewTag.reviewTagName from ReviewReviewTag rrt where rrt.review.id = :reviewId")
    List<String> findReviewTagNameByReviewId(@Param("reviewId") Long reviewId);

    @Query("select rrt.reviewTag from ReviewReviewTag rrt where rrt.review.id = :reviewId")
    List<ReviewTag> findReviewTagByReviewId(Long reviewId);

}