package sudoku.buildlogic;

import sudoku.computationlogic.GameLogic;
import sudoku.persistance.LocalStorageImpl;
import sudoku.problemdomain.IStorage;
import sudoku.problemdomain.SudokuGame;
import sudoku.userinterface.IUserInterfaceContract;
import sudoku.userinterface.logic.ControlLogic;

import java.io.IOException;

public class SudokuBuildLogic {
    public static void build(IUserInterfaceContract.View userInterface) throws IOException {
        System.out.println("build worked");
        SudokuGame initialSate;
        IStorage storage = new LocalStorageImpl();

        try{
            System.out.println("go to storage");
            initialSate = storage.getGameData();
            System.out.println("Initial State Created");

        }catch (IOException e){
            initialSate = GameLogic.getnewGame();
            storage.updateGameData(initialSate);
        }

        System.out.println("go to event Listener");
        IUserInterfaceContract.EventListener uiLogic = new ControlLogic(storage, userInterface);

        userInterface.setListener(uiLogic);
        userInterface.updateBoard(initialSate);


    }
}
