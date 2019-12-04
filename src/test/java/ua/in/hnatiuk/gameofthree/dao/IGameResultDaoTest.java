package ua.in.hnatiuk.gameofthree.dao;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ua.in.hnatiuk.gameofthree.App;
import ua.in.hnatiuk.gameofthree.model.GameResult;
import ua.in.hnatiuk.gameofthree.util.UserType;

import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {App.class})
public class IGameResultDaoTest {
    @Autowired
    private IGameResultDao gameResultDao;

    @Test
    public void saveEntityIntoDbAndFindByGameIdSuccess(){
        int expectedAmountOfTurns = 10;
        UserType expectedWinner = UserType.AI;
        UUID gameId = UUID.randomUUID();
        GameResult gameResult = new GameResult();
        gameResult.setAmountOfTurns(expectedAmountOfTurns);
        gameResult.setGameId(gameId);
        gameResult.setWinner(expectedWinner);
        gameResultDao.save(gameResult);

        Optional<GameResult> result = gameResultDao.findByGameId(gameId);
        assertThat(result.isPresent(), equalTo(Boolean.TRUE));
        GameResult persistedGameResult = result.get();
        assertThat(persistedGameResult.getAmountOfTurns(), equalTo(expectedAmountOfTurns));
        assertThat(persistedGameResult.getWinner(), equalTo(expectedWinner));
    }
}
