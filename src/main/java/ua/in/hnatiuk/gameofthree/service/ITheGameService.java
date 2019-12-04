package ua.in.hnatiuk.gameofthree.service;

import ua.in.hnatiuk.gameofthree.util.ResponseWrapper;

public interface ITheGameService {
    ResponseWrapper firstTurn(int user);
    ResponseWrapper nextTurn(long newValue);
}
