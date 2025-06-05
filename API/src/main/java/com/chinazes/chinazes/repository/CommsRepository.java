package com.chinazes.chinazes.repository;

import com.chinazes.chinazes.model.Comms;
import com.chinazes.chinazes.model.GroupedCommentableType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

public interface CommsRepository extends JpaRepository <Comms, Long> {
    @Query(value = "SELECT * FROM Comms c WHERE c.user_id=:user_id ORDER BY c.votes DESC", nativeQuery = true)
    List<Comms> findByUserID(@Param("user_id") long user_id);
    @Query(nativeQuery = true, value = "SELECT COUNT(*) FROM Comms c WHERE c.user_id=:user_id")
    int commsCount(@Param("user_id") long user_id);
    @Query(nativeQuery = true, value = "SELECT SUM(c.votes) FROM Comms c WHERE c.user_id=:user_id")
    int votesCount(@Param("user_id") long user_id);
    @Query(value = "SELECT * FROM Comms c ORDER BY c.votes DESC LIMIT 500", nativeQuery = true)
    List<Comms> commsByVotes();
    @Query(nativeQuery = true, value = "SELECT commentable_id, COUNT(*) as count, SUM(votes) as votes FROM comms WHERE commentable_type=:commentable_type GROUP BY commentable_id")
    List<GroupedCommentableType> groupedCommentableTypeByVotes(@Param("commentable_type") String beatmapset, Pageable pageable);
    @Query(nativeQuery = true, value = "SELECT commentable_id, COUNT(*) as c, SUM(votes) FROM comms WHERE commentable_type=:commentable_type GROUP BY commentable_id ORDER BY c DESC LIMIT 100")
    List<GroupedCommentableType> groupedCommentableTypeByCount(@Param("commentable_type") String beatmapset);
    @Query(value = "SELECT * FROM Comms WHERE msg_date BETWEEN :startDate AND :endDate", nativeQuery = true)
    List<Comms> commsBetweenDates(@Param("startDate")Date startDate, @Param("endDate")Date endDate, Pageable page);
}
