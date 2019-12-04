package ua.in.hnatiuk.gameofthree.service.impl;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import ua.in.hnatiuk.gameofthree.component.IIntegerGenerator;
import ua.in.hnatiuk.gameofthree.dao.IGameResultDao;
import ua.in.hnatiuk.gameofthree.dao.ITurnDao;
import ua.in.hnatiuk.gameofthree.model.Turn;
import ua.in.hnatiuk.gameofthree.service.ITheGameService;
import ua.in.hnatiuk.gameofthree.util.ResponseWrapper;
import ua.in.hnatiuk.gameofthree.util.TurnUtil;
import ua.in.hnatiuk.gameofthree.util.TurnType;
import ua.in.hnatiuk.gameofthree.util.UserType;

import java.util.List;
import java.util.UUID;

import static ua.in.hnatiuk.gameofthree.util.TurnUtil.makeTurn;

@Getter
@Setter
@Service
public class TheGameService implements ITheGameService {
    private long initialValue;
    private boolean isGameStarted;
    private UUID gameId;
    private long currentNumber;
    private List<TurnType> historyTurns;
    private IIntegerGenerator generator;
    private ITurnDao turnDao;
    private IGameResultDao gameResultDao;

    public TheGameService(IIntegerGenerator generator,
                          ITurnDao turnDao,
                          IGameResultDao gameResultDao
    ) {
        this.generator = generator;
        this.turnDao = turnDao;
        this.gameResultDao = gameResultDao;
    }

    public ResponseWrapper firstTurn(int user) throws RuntimeException{
         if (!isGameStarted()) {
             UserType userType = UserType.getUserType(user);
             setGameStarted(Boolean.TRUE);
             gameId = UUID.randomUUID();
             int firstNumber = generator.getRandomNumberInRange(0, 100);
             setInitialValue(firstNumber);
             setCurrentNumber(firstNumber);
             if (userType.equals(UserType.AI)) {
                 long newCurrentValue = makeTurn(currentNumber);
                 if (newCurrentValue == 1) {
                     /*save turn and game*/
                     isGameStarted = Boolean.FALSE;
                     return ResponseWrapper.builder()
                             .currentValue(1)
                             .initialValue(initialValue)
                             .message("The game is finished.")
                             .winner(UserType.AI)
                             .status(200)
                             .build();
                 }
                 /*save turn*/
                 setCurrentNumber(newCurrentValue);
             }

             return ResponseWrapper.builder()
                     .currentValue(currentNumber)
                     .initialValue(initialValue)
                     .message("The next turn.")
                     .status(200)
                     .build();
         }

         return ResponseWrapper.builder()
                 .currentValue(currentNumber)
                 .initialValue(initialValue)
                 .message("The game already started.")
                 .status(406)
                 .build();
     }

    @Override
    public ResponseWrapper nextTurn(long newValue) {
         if (!isGameStarted){
             return ResponseWrapper.builder()
                     .currentValue(currentNumber)
                     .initialValue(initialValue)
                     .message("The game is not started yet. Please start the game.")
                     .status(406)
                     .build();
         }

         if (!isValidNewValue(newValue)){

             return ResponseWrapper.builder()
                     .message("You sent wrong new value.Please try again.")
                     .currentValue(currentNumber)
                     .initialValue(initialValue)
                     .status(406)
                     .build();
         }
         if (newValue == 1){
             isGameStarted = Boolean.FALSE;
             /*save turn and game*/
             return ResponseWrapper.builder()
                     .currentValue(1)
                     .initialValue(initialValue)
                     .message("The game is finished.")
                     .winner(UserType.HUMAN)
                     .status(200)
                     .build();
         } else {
            if (TurnUtil.isTurnValid(TurnType.ZERO, newValue)){
                currentNumber = (newValue + TurnType.ZERO.value)/ 3;
                /*save turn*/
                if (currentNumber == 1){
                    isGameStarted = Boolean.FALSE;
                    /*save game*/
                    return ResponseWrapper.builder()
                            .currentValue(1)
                            .initialValue(initialValue)
                            .message("The game is finished.")
                            .winner(UserType.AI)
                            .status(200)
                            .build();
                }

                return getResponse();
            } else if(TurnUtil.isTurnValid(TurnType.MINUS_ONE, newValue)){
                currentNumber = (newValue + TurnType.MINUS_ONE.value)/ 3;
                /*save turn*/
                if (currentNumber == 1){
                    isGameStarted = Boolean.FALSE;
                    /*save game*/
                    return ResponseWrapper.builder()
                            .currentValue(1)
                            .initialValue(initialValue)
                            .message("The game is finished.")
                            .winner(UserType.AI)
                            .status(200)
                            .build();
                }

                return getResponse();
            } else {
                currentNumber = (newValue + TurnType.PLUS_ONE.value)/ 3;
                /*save turn*/
                if (currentNumber == 1){
                    isGameStarted = Boolean.FALSE;
                    /*save game*/
                    return ResponseWrapper.builder()
                            .currentValue(1)
                            .initialValue(initialValue)
                            .message("The game is finished.")
                            .winner(UserType.AI)
                            .status(200)
                            .build();
                }

                return getResponse();
            }
         }
    }

    private boolean isValidNewValue(long newValue){
         if ((newValue * 3) + TurnType.MINUS_ONE.value == currentNumber){
             return Boolean.TRUE;
         } else  if ((newValue * 3) + TurnType.ZERO.value == currentNumber){
             return Boolean.TRUE;
         } else  if ((newValue * 3) + TurnType.PLUS_ONE.value == currentNumber){
             return Boolean.TRUE;
         } else {
             return Boolean.FALSE;
         }
    }

    private ResponseWrapper getResponse(){
         return ResponseWrapper.builder()
                 .initialValue(initialValue)
                 .currentValue(currentNumber)
                 .message("Not bad! :)")
                 .status(200)
                 .build();
    }

}
