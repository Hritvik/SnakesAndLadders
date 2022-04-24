package com.vik.snl;


import com.vik.snl.service.SnakeAndLadder;
import com.vik.snl.service.SnakeLadderGameService;
import javafx.util.Pair;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TestCollection {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        executeTestCases();

        bufferedWriter.write("Test cases executed");
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }

    private static void executeTestCases() {
        SnakeAndLadder gameApp = new SnakeLadderGameService();
        Map<Integer, Integer> snakes = new HashMap<>();
        Map<Integer, Integer> ladders = new HashMap<>();
        snakes.put(8, 2);
        ladders.put(4, 7);
        String gameId = gameApp.createGame(3, snakes, ladders, Arrays.asList(1));

        print(1, !gameApp.holdDice("abc", 1)); // gameId doesn't exist
        print(2, !gameApp.holdDice(gameId, 2)); // player not part
        print(3, !gameApp.rollDiceAndMove("abc", 1)); // gameId doesn't exist
        print(4, !gameApp.rollDiceAndMove(gameId, 2)); // player not part
        print(5, gameApp.holdDice(gameId, 1)); // should be true as there is only one player
        int i = 0;
        while (i < 1000) { // should win
            gameApp.holdDice(gameId, 1);
            if (!gameApp.rollDiceAndMove(gameId, 1)) {
                break;
            }
            i++;
        }
        Pair
        print(6, i != 1000);
        print(7, !gameApp.holdDice(gameId, 1)); // game is already ended
        print(8, !gameApp.rollDiceAndMove(gameId, 1)); // game is already ended

        String gameId2 = gameApp.createGame(3, snakes, ladders, Arrays.asList(1, 2));
        print(9, gameApp.holdDice(gameId2, 1) && !gameApp.holdDice(gameId2, 2)); // hold dice
        print(10, !gameApp.rollDiceAndMove(gameId2, 2)); // player not holding dice calls
    }

    private static void print(int testCaseNumber, boolean success) {
        if (success) {
            System.out.println(String.format("Test case: %d - Success", testCaseNumber));
        } else {
            System.out.println(String.format("Test case: %d - Failed", testCaseNumber));
        }
    }
}