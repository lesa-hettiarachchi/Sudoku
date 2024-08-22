package sudoku.problemdomain;

import sudoku.constants.GameState;
import sudoku.computationlogic.SudokuUtilities;

import java.io.Serializable;

public class SudokuGame implements Serializable {
    private final GameState gameState;
    private final int [][] gridState;

    public static final int GRID_BOUNDRY = 9;

    public SudokuGame(GameState gameState, int[][] gridState) {
        System.out.println("SudokuGame constructor");
        this.gameState = gameState;
        this.gridState = gridState;
    }

    public GameState getGameState() {
        System.out.println("game stateCreated");
        return gameState;
    }

    public int[][] getCopyOfGridState() {
        return SudokuUtilities.copyToNewArray(gridState);
    }
}
