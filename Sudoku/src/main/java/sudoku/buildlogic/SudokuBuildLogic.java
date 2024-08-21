package sudoku.buildlogic;

import sudoku.computationlogic.GameLogic;
import sudoku.persistance.LocalStorageImpl;
import sudoku.problemdomain.IStorage;
import sudoku.problemdomain.SudokuGame;
import sudoku.userinterface.IUserInterfaceContract;
import sudoku.userinterface.UserInterfaceImpl;
import sudoku.userinterface.logic.ControlLogic;

import java.io.IOException;

public class SudokuBuildLogic {
    public static void build(IUserInterfaceContract.View userInterface) throws IOException {
        SudokuGame initialSate;
        IStorage storage = new LocalStorageImpl();

        try{
            initialSate = storage.getGameData();

        }catch (IOException e){
            initialSate = GameLogic.getnewGame();
            storage.updateGameData(initialSate);
        }

        IUserInterfaceContract.EventListener uiLogic = new ControlLogic(storage, userInterface );

        userInterface.setListener(uiLogic);
        userInterface.updateBoard(initialSate);


    }
}
