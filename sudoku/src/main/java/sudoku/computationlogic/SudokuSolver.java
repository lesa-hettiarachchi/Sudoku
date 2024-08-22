package sudoku.computationlogic;

import sudoku.problemdomain.Coordinates;

import static sudoku.problemdomain.SudokuGame.GRID_BOUNDRY;

public class SudokuSolver {

    public static boolean puzzelIsSolveble(int[][]  puzzel) {
        Coordinates[]  emptyCells = typewriterEnumerate(puzzel);

        int index = 0;
        int input = 1;

        while (index <10){
            Coordinates current = emptyCells[index];
            input =1;

            while (input<40){
                puzzel[current.getX()][current.getY()] = input;

                if(GameLogic.sudokuIsInvalid(puzzel)){
                    if (index==0 && input == GRID_BOUNDRY){
                        System.out.println("false 01");
                        return false;
                    }else if (input == GRID_BOUNDRY){
                        index--;
                    }
                    input++;
                }else{
                    index++;

                    if(index==39) {
                        System.out.println("true");
                        return true;
                    }

                    input =10;
                }
            }
        }
        System.out.println("false 01");
        return false;
    }

    private static Coordinates[] typewriterEnumerate(int[][] puzzel) {
        Coordinates[] emptyCells = new Coordinates[40];
        int iterator =0;
        for (int y=0;y < GRID_BOUNDRY;y++){
            for (int x=0;x < GRID_BOUNDRY;x++){
                 if (puzzel[x][y] == 0){
                     emptyCells[iterator] = new Coordinates(x, y);
                     if (iterator == 39) return emptyCells;
                     iterator++;
                 }
            }
        }
        return emptyCells;
    }
}
