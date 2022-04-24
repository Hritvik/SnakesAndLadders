package com.vik.snl.service;

import java.util.List;
import java.util.Map;

 public interface SnakeAndLadder {

    /*
    return unique game id
    */
    String createGame(int boardSize, Map<Integer, Integer> snakes, Map<Integer, Integer> ladders, List<Integer> playerIds);

    /*
    return false
        - if player not part of this game
        - if the game is already ended
        - if the game id doesn't exist
        - if dice already allocated
    return true if hold dice is succeeded
    */
    Boolean holdDice(String gameId, int playerId);

    /*
    return false 
        - if any player who doesn't hold the dice calls this
        - if dice is not allocated
        - if the game is already ended
        - if the game id doesn't exist
    otherwise roll dice and move and return true
    */
    Boolean rollDiceAndMove(String gameId, int playerId);
}