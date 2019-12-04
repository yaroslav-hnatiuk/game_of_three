package ua.in.hnatiuk.gameofthree.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import ua.in.hnatiuk.gameofthree.util.TurnType;
import ua.in.hnatiuk.gameofthree.util.UserType;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "turn")
public class Turn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Type(type = "org.hibernate.type.UUIDCharType")
    @Column(name = "game_id")
    private UUID gameId;
    @Column(name = "turn_type")
    private TurnType turnType;
    @Column(name = "user_type")
    private UserType userType;
}
