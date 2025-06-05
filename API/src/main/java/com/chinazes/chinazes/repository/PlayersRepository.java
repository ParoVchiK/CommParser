package com.chinazes.chinazes.repository;

import com.chinazes.chinazes.model.Playeralt;
import com.chinazes.chinazes.model.Players;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlayersRepository extends JpaRepository<Players, Long> {
    @Query(nativeQuery = true, value = "SELECT id FROM players LIMIT 1000 OFFSET 1000*(:page-1)")
    List<Integer> getPlayerIDs(@Param("page") int page);
    @Query(nativeQuery = true, value = "SELECT * FROM players ORDER BY votes_up DESC LIMIT 100")
    List<Players> getPlayersByVotes();
    @Query(nativeQuery = true, value = "SELECT * FROM players ORDER BY comms_count DESC LIMIT 100")
    List<Players> getPlayersByCount();
    @Query(nativeQuery = true, value = "SELECT user_id, SUM(votes) as votes_up, COUNT(*) as comms_count FROM comms GROUP BY user_id ORDER BY votes_up DESC LIMIT 500")
    List<Playeralt> getPlayersByVotesAlt();
    @Query(nativeQuery = true, value = "SELECT user_id, SUM(votes) as votes_up, COUNT(*) as comms_count FROM comms GROUP BY user_id ORDER BY comms_count DESC LIMIT 500")
    List<Playeralt> getPlayersByCountAlt();
}
