package edwang.tictactoejava.model;

/**
 * Created by Edward Wang on 10/18/2017.
 */

public class TicTacToeModel {
    private static TicTacToeModel instance = null;

    // Make sure to have only 1 TicTactoe game
    public static TicTacToeModel getInstance() {
        if (instance == null) {
            instance = new TicTacToeModel();
        }
        return instance;
    }

    public TicTacToeModel() {

    }

    // potiential value for a location
    public static final short EMPTY = 0;
    public static final short CIRCLE = 1;
    public static final short CROSS = 2;

    // represents board
    private short[][] model = {
            {EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY}
    };

    private short nextPlayer = CIRCLE;

    public void resetGame() {
        nextPlayer = CIRCLE;
        for (int x = 0; x <3; x++) {
            for (int y = 0; y < 3; y++) {
                model[x][y] = EMPTY;
            }
        }
    }

    public short getFieldContent(int x, int y) {
        return model[x][y];
    }

    public void setFieldContent(int x, int y, short player) {
        model[x][y] = player;
    }

    public short getNextPlayer() {
        return nextPlayer;
    }

    public void switchPlayers() {
        nextPlayer = (nextPlayer == CIRCLE) ? CROSS : CIRCLE;
    }
}