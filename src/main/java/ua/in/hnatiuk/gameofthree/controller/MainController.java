package ua.in.hnatiuk.gameofthree.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.in.hnatiuk.gameofthree.service.ITheGameService;
import ua.in.hnatiuk.gameofthree.util.ResponseWrapper;

@Slf4j
@RestController
public class MainController {
    private ITheGameService game;

    public MainController(ITheGameService game) {
        this.game = game;
    }

    @GetMapping(path = "/start-game")
    public ResponseEntity<ResponseWrapper> startGame(@RequestParam("first_turn") byte user) {
        try{
            ResponseWrapper response = game.firstTurn(user);

            return ResponseEntity.status(response.getStatus()).body(response);
        } catch (RuntimeException e){
            log.error(e.getMessage());
            ResponseWrapper response = ResponseWrapper.builder()
                    .message(e.getMessage())
                    .status(406)
                    .build();

            return ResponseEntity.status(406).body(response);
        }
    }

    @PutMapping(path = "/next-turn")
    public ResponseEntity<ResponseWrapper> makeNextTurn(@RequestParam("new_value") long newValue) {
            ResponseWrapper response = game.nextTurn(newValue);
            return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping(path = "")
    public ResponseEntity<ResponseWrapper> getGameInfo(){
        return null;
    }
}
