package ua.in.hnatiuk.gameofthree.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.in.hnatiuk.gameofthree.model.Turn;

public interface ITurnDao extends JpaRepository<Turn, Long> {
}
