package ua.in.hnatiuk.gameofthree.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import ua.in.hnatiuk.gameofthree.util.UserType;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "game_result")
public class GameResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Type(type = "org.hibernate.type.UUIDCharType")
    @Column(name = "game_id")
    private UUID gameId;
    @Column(name = "winner_type")
    private UserType winner;
    @Column(name = "amount_of_turn")
    private int amountOfTurns;

}
