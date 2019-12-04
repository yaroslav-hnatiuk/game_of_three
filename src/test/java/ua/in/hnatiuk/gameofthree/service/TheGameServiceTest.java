package ua.in.hnatiuk.gameofthree.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ua.in.hnatiuk.gameofthree.App;
import ua.in.hnatiuk.gameofthree.component.IIntegerGenerator;
import ua.in.hnatiuk.gameofthree.service.impl.TheGameService;
import ua.in.hnatiuk.gameofthree.util.ResponseWrapper;
import ua.in.hnatiuk.gameofthree.util.UserType;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {App.class})
public class TheGameServiceTest {
    @Autowired
    private TheGameService theGameService;
    @MockBean
    private IIntegerGenerator integerGenerator;

     /* ITheGameService#firstTurn tests */
    @Test
    public void firstTurnReturnsStatus404WhenGameAlreadyStarted(){
        theGameService.setGameStarted(Boolean.TRUE);
        int expectedStatus = 406;
        String expectedMessage = "The game already started.";
        ResponseWrapper response = theGameService.firstTurn(UserType.AI.ordinal());
        assertThat(response.getStatus(), equalTo(expectedStatus));
        assertThat(response.getMessage(), equalTo(expectedMessage));
    }

    /* ITheGameService#firstTurn returns currentValue equal to initialValue
        without changes when first turn make a Human*/
    @Test
    public void firstTurnMakeHumanSuccess(){
        theGameService.setGameStarted(Boolean.FALSE);
        int expectedStatus = 200;
        String expectedMessage = "The next turn.";
        ResponseWrapper response = theGameService.firstTurn(UserType.HUMAN.ordinal());
        assertThat(response.getStatus(), equalTo(expectedStatus));
        assertThat(response.getMessage(), equalTo(expectedMessage));
        assertThat(response.getCurrentValue(), equalTo(response.getInitialValue()));
    }

    /* ITheGameService#firstTurn returns currentValue not equal to initialValue
        without changes when first turn make a AI*/
    @Test
    public void firstTurnMakeAISuccess(){
        when(integerGenerator.getRandomNumberInRange(0, 100)).thenReturn(99);
        theGameService.setGameStarted(Boolean.FALSE);
        int expectedStatus = 200;
        String expectedMessage = "The next turn.";
        ResponseWrapper response = theGameService.firstTurn(UserType.AI.ordinal());
        assertThat(response.getStatus(), equalTo(expectedStatus));
        assertThat(response.getMessage(), equalTo(expectedMessage));
        assertThat(response.getCurrentValue(), not(equalTo(response.getInitialValue())));
    }

    /* ITheGameService#firstTurn if AI make firs turn and initialValue is
    3 AI win the game*/
    @Test
    public void AiWinTheGameSuccess(){
        when(integerGenerator.getRandomNumberInRange(0, 100)).thenReturn(3);
        int expectedStatus = 200;
        String expectedMessage = "The game is finished.";
        long expectedCurrentValue = 1;
        theGameService.setGameStarted(Boolean.FALSE);
        ResponseWrapper response = theGameService.firstTurn(UserType.AI.ordinal());
        assertThat(response.getStatus(), equalTo(expectedStatus));
        assertThat(response.getMessage(), equalTo(expectedMessage));
        assertThat(response.getCurrentValue(), equalTo(expectedCurrentValue));
    }

    /*
     * ITheGameService#firstTurn tests
     */
    @Test
    public void nextTurnReturnStatus406WhenGameWasNotStarted(){
        String expectedMessage = "The game is not started yet. Please start the game.";
        int expectedStatus = 406;
        long anyValueForTheNextTurn = 100;
        theGameService.setGameStarted(Boolean.FALSE);
        ResponseWrapper res = theGameService.nextTurn(anyValueForTheNextTurn);
        assertThat(res.getMessage(), equalTo(expectedMessage));
        assertThat(res.getStatus(), equalTo(expectedStatus));
    }

    /*Method ITheGameService#firstTurn return response with status 406 when game is started
    * but newValue is not valid*/
    @Test
    public void nextTurnWithInvalidNextValue(){
        String expectedMessage = "You sent wrong new value.Please try again.";
        int expectedStatus = 406;
        long invalidNewValue = 4;
        theGameService.setGameStarted(Boolean.TRUE);
        theGameService.setCurrentNumber(10);
        ResponseWrapper res = theGameService.nextTurn(invalidNewValue);
        assertThat(res.getMessage(), equalTo(expectedMessage));
        assertThat(res.getStatus(), equalTo(expectedStatus));
    }

    /*The HUMAN win the game if newValue is valid and equal to 1*/
    @Test
    public void humanWinTheGameSuccess(){
        String expectedMessage = "The game is finished.";
        int expectedStatus = 200;
        UserType expectedWinner = UserType.HUMAN;
        long validNewValue = 1;
        theGameService.setGameStarted(Boolean.TRUE);
        theGameService.setCurrentNumber(3);
        ResponseWrapper res = theGameService.nextTurn(validNewValue);
        assertThat(res.getMessage(), equalTo(expectedMessage));
        assertThat(res.getStatus(), equalTo(expectedStatus));
        assertThat(res.getWinner(), equalTo(expectedWinner));
    }

    /*If newValue is valid and not equal to 1* and will be not equal to 1 after AI turn
    so current value will be changed*/
    @Test
    public void nextTurnUpdateCurrentValueSuccess(){
        String expectedMessage = "Not bad! :)";
        int expectedStatus = 200;
        long currentValue = 30;
        /*After AI turn currentValue will be changed*/
        long expectedUpdatedCurrentValue = 3;
        long validNewValue = 10;
        theGameService.setGameStarted(Boolean.TRUE);
        theGameService.setCurrentNumber(currentValue);
        ResponseWrapper res = theGameService.nextTurn(validNewValue);
        assertThat(res.getMessage(), equalTo(expectedMessage));
        assertThat(res.getStatus(), equalTo(expectedStatus));
        assertThat(res.getCurrentValue(), equalTo(expectedUpdatedCurrentValue));
    }

    /*If newValue is valid and not equal to 1* and will be equal to 1 after AI turn
   so AI win the game*/
    @Test
    public void nextTurnAiWinTheGameSuccess(){
        String expectedMessage = "The game is finished.";
        int expectedStatus = 200;
        long currentValue = 10;
        long validNewValue = 3;
        UserType expectedWinner = UserType.AI;
        theGameService.setGameStarted(Boolean.TRUE);
        theGameService.setCurrentNumber(currentValue);
        ResponseWrapper res = theGameService.nextTurn(validNewValue);
        assertThat(res.getMessage(), equalTo(expectedMessage));
        assertThat(res.getStatus(), equalTo(expectedStatus));
        assertThat(res.getWinner(), equalTo(expectedWinner));
    }
}
