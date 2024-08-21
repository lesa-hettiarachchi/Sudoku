package sudoku.computationlogic;

import sudoku.problemdomain.Coordinates;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import static sudoku.problemdomain.SudokuGame.GRID_BOUNDRY;
public class GameGenerator {
    public static int[][] getNewGameGrid(){
        return unsolveGame(getSolvedGame());
     }

    private static int[][] unsolveGame(int[][] solvedGame) {
        Random random = new Random(System.currentTimeMillis());

        boolean solveable =false;
        int[][] solvebleArray = new int [GRID_BOUNDRY][GRID_BOUNDRY];

        while(solveable==false){
            SudokuUtilities.copySudokuArrayValues(solvedGame, solvebleArray);
            int index =0;

            while(index<40){
                int xCoordinate = random.nextInt(GRID_BOUNDRY);
                int yCoordinate = random.nextInt(GRID_BOUNDRY);

                if (solvebleArray[xCoordinate][yCoordinate] !=0){
                    solvebleArray[xCoordinate][yCoordinate] = 0;
                    index++;
                }
            }

            int [][] toBeSolved = new int[GRID_BOUNDRY][GRID_BOUNDRY];
            SudokuUtilities.copySudokuArrayValues(solvebleArray, toBeSolved);

            solveable = SudokuSolver.puzzelIsSolveble(toBeSolved);

        }
        return solvebleArray;
    }

    private static int[][] getSolvedGame() {
        Random random = new Random(System.currentTimeMillis());
        int [][]newGrid = new int [GRID_BOUNDRY][GRID_BOUNDRY];

        for(int value =1 ; value <= GRID_BOUNDRY ; value++){
            int allocations =0;
            int interrupt =0;

            List<Coordinates> allocTracker = new ArrayList<>();

            int attempts =0;

            while(allocations< GRID_BOUNDRY){
                if (interrupt >200){
                     allocTracker.forEach(coord->{
                         newGrid[coord.getX()][coord.getY()] = 0;
                     });

                     interrupt =0;
                     allocations =0;
                     allocTracker.clear();
                     attempts++;

                     if (attempts >500){
                          clearArray(newGrid);
                           attempts = 0;
                           value=1;

                     }
                }

                int xCoordinate = random.nextInt(GRID_BOUNDRY);
                int yCoordinate = random.nextInt(GRID_BOUNDRY);

                if (newGrid[xCoordinate][yCoordinate] == 0){
                    newGrid[xCoordinate][yCoordinate] = value;

                    if(GameLogic.sudokuIsInvalid(newGrid)){
                        newGrid[xCoordinate][yCoordinate] = 0;
                        interrupt ++;
                    }else{
                        allocTracker.add(new Coordinates(xCoordinate, yCoordinate));

                    }
                }
            }
        }
        return newGrid;
    }

    private static void clearArray(int[][] newGrid) {
         for (int xIndex =0; xIndex < GRID_BOUNDRY; xIndex++) {
             for (int yIndex =0; yIndex < GRID_BOUNDRY; yIndex++) {
                  newGrid[xIndex][yIndex] = 0;
             }
         }
    }
}
