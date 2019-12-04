package ua.in.hnatiuk.gameofthree.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.in.hnatiuk.gameofthree.model.GameResult;

import java.util.Optional;
import java.util.UUID;

public interface IGameResultDao extends JpaRepository<GameResult, Long> {
    Optional<GameResult> findByGameId(UUID gameId);
}
