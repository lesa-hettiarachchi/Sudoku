package sudoku.persistance;

import sudoku.problemdomain.IStorage;
import sudoku.problemdomain.SudokuGame;

import java.io.*;

public class LocalStorageImpl implements IStorage {
    private static File GAME_DATA = new File(
            System.getProperty("user.ramani"),
            "game-data.txt"
    );

    @Override
    public void updateGameData(SudokuGame game) throws IOException {
        System.out.println("Came to update");
        try{
            System.out.println("Updating game");
            FileOutputStream fileOutputStream = new FileOutputStream(GAME_DATA);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(game);
            objectOutputStream.close();
            System.out.println("Updated game");
        }catch(IOException e){
            throw new IOException("Unable to access Game Data");
        }
    }

    @Override
    public SudokuGame getGameData() throws IOException {
        System.out.println();
        FileInputStream fileInputStream = new FileInputStream(GAME_DATA);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        try {
            SudokuGame gameState = (SudokuGame) objectInputStream.readObject();
            objectInputStream.close();
            return gameState;
        }catch (IOException | ClassNotFoundException e){
            throw new IOException("Unable to access Game Data");
        }
    }
}
