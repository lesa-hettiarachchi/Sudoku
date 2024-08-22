package sudoku.userinterface;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sudoku.constants.GameState;
import sudoku.problemdomain.Coordinates;
import sudoku.problemdomain.SudokuGame;

import java.util.HashMap;

public class UserInterfaceImpl implements IUserInterfaceContract.View, EventHandler<KeyEvent> {

    private final Stage stage;
    private final Group root;

    private HashMap<Coordinates,SudokuTextField> textFieldCoordinates;
    private IUserInterfaceContract.EventListener listener;

    private static final double WINDOW_Y = 732;
    private static final double WINDOW_X= 668;
    private static final double BOARDER_PADDING = 50;
    private static final double BOARDER_X_AND_Y = 576;

    private static final Color WINDOW_BACKGROUND_COLOR = Color.rgb(17,48,99);
    private static final Color BOARD_BACKGROUD_COLOR = Color.rgb(224,242,241);
    private static final String SUDOKU = "Sudoku";

    public UserInterfaceImpl(Stage stage) {
        this.stage = stage;
        this.root = new Group();
        this.textFieldCoordinates = new HashMap<>();
        initializeUserInterface();
    }

    @Override
    public void setListener(IUserInterfaceContract.EventListener listener) {
        this.listener = listener;
    }

    public void initializeUserInterface() {
        drawBackground(root);
        drawTitle(root);
        drawSudokuBoard(root);
        drawTextFields(root);
        drawGridLines(root);
        System.out.println("gridlines done");
        stage.show();
        System.out.println("Show Worked");

    }

    private void drawTextFields(Group root) {
        final int xOrigin =50;
        final int yOrigin =50;

        final int xAndYDelta =64;

        for(int xIndex = 0; xIndex < 9; xIndex++){
            for(int yIndex = 0; yIndex < 9; yIndex++){

                int x= xOrigin + xIndex * xAndYDelta;
                int y= yOrigin + yIndex * xAndYDelta;

                SudokuTextField tile = new SudokuTextField(xIndex,yIndex);

                styleSudokuTile(tile,x,y);

                tile.setOnKeyPressed(this);

                textFieldCoordinates.put(new Coordinates(xIndex,yIndex),tile);

                root.getChildren().add(tile);
            }
        }
    }

    private void styleSudokuTile(SudokuTextField tile, int x, int y) {

        Font numberFont = new Font(32);

        tile.setFont(numberFont);
        tile.setAlignment(Pos.CENTER);

        tile.setLayoutX(x);
        tile.setLayoutY(y);
        tile.setPrefHeight(64);
        tile.setPrefWidth(64);

        tile.setBackground(Background.EMPTY);

    }

    private void drawGridLines(Group root) {
        int xAndy = 114;
        int index =0;
        while(index < 8){
            int thickness;
            if (index == 2 || index == 5){
                thickness = 3;
            }else{
                thickness = 2;
            }
            Rectangle verticalLine = getLine(
                    xAndy + 64 * index,
                    BOARDER_PADDING,
                    BOARDER_X_AND_Y,
                    thickness
            );
            Rectangle horizontalLine = getLine(
                    BOARDER_PADDING,
                    xAndy + 64 * index,
                    thickness,
                    BOARDER_X_AND_Y
            );

            root.getChildren().addAll(verticalLine,horizontalLine);

            index++;
        }
        System.out.println("REC");
    }

    public Rectangle getLine(double x, double y, double height, double width)   {
        System.out.println("rec");
        Rectangle line = new Rectangle();
        line.setX(x);
        line.setY(y);
        line.setHeight(height);
        line.setWidth(width);

        line.setFill(Color.BLACK);
        return line;
    }

    public void drawBackground(Group root) {
        Scene scene = new Scene(root, WINDOW_X, WINDOW_Y,BOARD_BACKGROUD_COLOR);
        scene.setFill(WINDOW_BACKGROUND_COLOR);
        stage.setScene(scene);
        System.out.println("Drawing background Calls");

    }

    private void drawSudokuBoard(Group root) {
        Rectangle boardBackground = new Rectangle();
        boardBackground.setX(BOARDER_PADDING);
        boardBackground.setY(BOARDER_PADDING);

        boardBackground.setWidth( BOARDER_X_AND_Y);
        boardBackground.setHeight(BOARDER_X_AND_Y);

        boardBackground.setFill(BOARD_BACKGROUD_COLOR);

        root.getChildren().addAll(boardBackground);

    }

    private void drawTitle(Group root) {
        Text title = new Text(235,690,SUDOKU);
        title.setFill(Color.WHITE);
        Font titleFont = new Font(43);
        title.setFont(titleFont);
        root.getChildren().add(title);
    }

    @Override
    public void updateSquare(int x, int y, int input) {
        SudokuTextField tile = textFieldCoordinates.get(new Coordinates(x,y));

        String value = Integer.toString(input);

        if (value.equals("0")) value = "";

        tile.textProperty().setValue(value);
    }

    @Override
    public void updateBoard(SudokuGame game) {
        for (int xIndex = 0; xIndex < 9; xIndex++){
            for (int yIndex = 0; yIndex < 9; yIndex++){
                TextField tile = textFieldCoordinates.get(new Coordinates(xIndex,yIndex));

                String value = Integer.toString(
                        game.getCopyOfGridState()[xIndex][yIndex]
                );

                if (value.equals("0")) value = "";
                tile.setText(value);

                if (game.getGameState() == GameState.NEW){
                    if (value.equals("")){
                        tile.setStyle("-fx-opacity: 1");
                        tile.setDisable(false);
                    } else {
                        tile.setStyle("-fx-opacity: 0.8");
                        tile.setDisable(true);
                    }
                }
            }
        }
    }

    @Override
    public void showDialog(String message) {
        Alert dialog = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.OK);
        dialog.showAndWait();

        if (dialog.getResult() == ButtonType.OK) listener.onDialogClick();
    }

    @Override
    public void showError(String message) {
        Alert dialog = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        dialog.showAndWait() ;

    }

    @Override
    public void handle(KeyEvent event) {
        if(event.getEventType()== KeyEvent.KEY_PRESSED){
            if(event.getText().matches("[0-9]")){
                int value = Integer.parseInt(event.getText());
                handleInput( value,event.getSource());
            }else if( event.getCode()== KeyCode.BACK_SPACE){
                handleInput(0,event.getSource());
            }else{
                ((TextField) event.getSource()).setText("");
            }
        }
        event.consume();
    }

    private void handleInput(int value, Object source) {
        listener.onSudokuInput(
                ((SudokuTextField) source).getX(),
                ((SudokuTextField) source).getY(),
                value
        );

    }
}
