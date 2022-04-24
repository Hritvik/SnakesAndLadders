package com.vik.snl.utils;

public class CommonMethods {
    public static int[] toCoordinates(int n, int pos) {
        int[] coordinates = {pos / n, pos % n};
        return coordinates;
    }

    public static int toPos(int n, int[] coordinates) {
        return coordinates[0] * n + coordinates[1];
    }

    public static void printBoard(int[][] board) {
        int boardSize = board.length;
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
