package com.vik.snl.service;

import com.vik.snl.utils.CommonMethods;
import com.vik.snl.utils.GameStatus;
import com.vik.snl.utils.RandomUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SnakeLadderGameService implements SnakeAndLadder, ModifyCellLimit {
    private String gameId;
    private int boardSize;
    private Map<Integer, Integer> snakes;
    private Map<Integer, Integer> ladders;
    private Map<Integer, Integer> playerPositions;
    private int[][] board;
    private GameStatus gameStatus;
    private Dice dice;
    private int end;
    private int winner;

    /*
    return unique game id
    */
    public String createGame(int boardSize, Map<Integer, Integer> snakes, Map<Integer, Integer> ladders, List<Integer> playerIds) {
        this.boardSize = boardSize;
        this.snakes = snakes;
        this.ladders = ladders;
        this.playerPositions = new HashMap<>();
        for (int playerId : playerIds) {
            playerPositions.put(playerId, 0);
        }
        this.board = new int[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                board[i][j] = 1;
            }
        }
        dice = new Dice();
        board[0][0] = playerPositions.size();
        this.gameId = RandomUtils.generateId();
        this.gameStatus = GameStatus.OPEN;
        this.end = boardSize * boardSize;
        return this.gameId;
    }

    /*
    return false
        - if player not part of this game
        - if the game is already ended
        - if the game id doesn't exist
        - if dice already allocated
    return true if hold dice is succeeded
    */
    public synchronized Boolean holdDice(String gameId, int playerId) {
        if (gameId != null && this.gameId.equals(gameId) && gameStatus == GameStatus.OPEN && playerPositions.containsKey(playerId) && !dice.isAllocated()) {
            dice.setHeldBy(playerId);
//            CommonMethods.printBoard(board);
            return true;
        }
        return false;
    }

    /*
    return false
        - if any player who doesn't hold the dice calls this
        - if dice is not allocated
        - if the game is already ended
        - if the game id doesn't exist
    otherwise roll dice and move and return true
    */
    public Boolean rollDiceAndMove(String gameId, int playerId) {
        if (gameId != null && this.gameId.equals(gameId) && gameStatus == GameStatus.OPEN && dice.isAllocated() && dice.getHeldBy() == playerId) {
            int roll = dice.roll();
            int priorPosition = playerPositions.get(playerId);
            int tentativePosition = priorPosition + roll;
            if (tentativePosition == end) {
                gameStatus = GameStatus.CLOSED;
                winner = playerId;
            } else if (tentativePosition > end) {
                // Turn wasted;
            } else {
                while(snakes.containsKey(tentativePosition) || ladders.containsKey(tentativePosition)){
                    if (snakes.containsKey(tentativePosition)) {
                        tentativePosition = snakes.get(tentativePosition);
                    } else if (ladders.containsKey(tentativePosition)) {
                        tentativePosition = ladders.get(tentativePosition);
                    }
                }
                int[] tCoordinates = CommonMethods.toCoordinates(boardSize, tentativePosition);
                if (board[tCoordinates[0]][tCoordinates[1]] > 0) {
                    playerPositions.put(playerId, tentativePosition);
                    board[tCoordinates[0]][tCoordinates[1]]--;
                    int[] pCoordinates = CommonMethods.toCoordinates(boardSize, priorPosition);
                    board[pCoordinates[0]][pCoordinates[1]]++;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean setLimit(int i, int j, int limit) {
        if (i >= 0 && i < boardSize && j >= 0 && j < boardSize && limit > 0 && limit <= playerPositions.size()) {
            this.board[i][j] = limit;
            return true;
        }
        return false;
    }
}
