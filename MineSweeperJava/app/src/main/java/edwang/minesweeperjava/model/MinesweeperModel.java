package edwang.minesweeperjava.model;

import java.util.Random;


public class MinesweeperModel {

    private static MinesweeperModel instance = null;

    public static final short EMPTY = 0;
    public static short numberOfBombs = 3;
    public static final short BOMB = (short) (numberOfBombs + 1);
    public static int dimension = 5;
    public int bombsFound = 0;


    private int randomNumX = 0;
    private int randomNumY = 0;

    private short[][] mineFieldModel = new short[dimension][dimension];
    private short[][] hasClickedField = new short[dimension][dimension];

    public static final short TOGGLE_CLICK = 0;
    public static final short TOGGLE_FLAG = 1;
    public static final short NOT_CLICKED = 2;
    private int currentToggle = 0;

    public static MinesweeperModel getInstance() {
        if (instance == null) {
            instance = new  MinesweeperModel();
        }
        return instance;
    }

    public MinesweeperModel() {
        populateBombs();
        populateHasClickedField();
        populateMineFieldModel();
    }

    private void populateBombs() {
        int bombCounter = 0;
        while (bombCounter < numberOfBombs) {
            generateRandomNumber();
            if (mineFieldModel[randomNumX][randomNumY] != BOMB) {
                mineFieldModel[randomNumX][randomNumY] = BOMB;
                bombCounter++;
            }
        }
    }



    public String checkWinLoss(int x, int y) {
        String message = "";
        // clicked on a bomb
        if (mineFieldModel[x][y] == BOMB && currentToggle == TOGGLE_CLICK ) {
            message = "YOU LOST! Clicked a bomb";

        }
        // flagged a bomb
        if (mineFieldModel[x][y] == BOMB && currentToggle == TOGGLE_FLAG) {
            bombsFound++;
            message = "Bomb found! You have found: " + bombsFound;
        }
        // flagged not on a bomb
        if (mineFieldModel[x][y] != BOMB && currentToggle == TOGGLE_FLAG) {
            message = "YOU LOST! Flagged a false bomb";
        }

        // win
        if (bombsFound == numberOfBombs) {
            message = "CONGRATULATIONS! You found the all the bombs";
        }

        return message;
    }

    private void populateHasClickedField() {
        for (int x = 0; x < dimension; x++) {
            for (int y = 0; y < dimension; y++) {
                hasClickedField[x][y] = NOT_CLICKED;
            }
        }
    }

    private void populateMineFieldModel() {
        for (int x = 0; x < dimension; x++) {
            for (int y = 0; y < dimension; y++) {
                if (mineFieldModel[x][y] != BOMB) {
                    short nearbyBomb = 0;
                    mineFieldModel[x][y] = EMPTY;

                    // left up
                    if (!(x == 0 || y == 0) && (mineFieldModel[x - 1][y - 1] == BOMB)) {
                        nearbyBomb++;
                    }
                    //up
                    if (!(x == 0) && mineFieldModel[x - 1][y] == BOMB) {
                        nearbyBomb++;
                    }
                    //right up
                    if (!(x == 0 || y == dimension - 1) && mineFieldModel[x - 1][y + 1] == BOMB) {
                        nearbyBomb++;
                    }
                    // right
                    if (!(y == dimension - 1) && mineFieldModel[x][y + 1] == BOMB) {
                        nearbyBomb++;
                    }
                    // down right
                    if (!(x == dimension - 1 || y == dimension - 1) && mineFieldModel[x + 1][y + 1] == BOMB) {
                        nearbyBomb++;
                    }
                    // down
                    if (!(x == dimension - 1) && mineFieldModel[x + 1][y] == BOMB) {
                        nearbyBomb++;
                    }
                    // left down
                    if (!(y == 0 || x == dimension - 1) && mineFieldModel[x + 1][y - 1] == BOMB) {
                        nearbyBomb++;
                    }
                    // left
                    if (!(y == 0) && mineFieldModel[x][y - 1] == BOMB) {
                        nearbyBomb++;
                    }
                    if (nearbyBomb > 0) {
                        mineFieldModel[x][y] = nearbyBomb;
                    }
                }
            }
        }
    }

    public void setHasClickedField(int x, int y, short value) {
        hasClickedField[x][y] = value;

        if (currentToggle == TOGGLE_CLICK && mineFieldModel[x][y] == EMPTY) {
            //up
            if (!(x == 0) && hasClickedField[x - 1][y] == NOT_CLICKED) {
                setHasClickedField(x-1, y, TOGGLE_CLICK);
            }
            // right
            if (!(y == dimension - 1) && hasClickedField[x][y + 1] == NOT_CLICKED) {
                setHasClickedField(x, y+1, TOGGLE_CLICK);
            }
            // down
            if (!(x == dimension - 1)&& hasClickedField[x+1][y] == NOT_CLICKED) {
                setHasClickedField(x+1, y, TOGGLE_CLICK);
            }
            // left
            if (!(y == 0) && hasClickedField[x][y - 1] == NOT_CLICKED) {
                setHasClickedField(x, y-1, TOGGLE_CLICK);
            }
            // left up
            if (!(x == 0 || y == 0) && (hasClickedField[x - 1][y - 1] == NOT_CLICKED)) {
                setHasClickedField(x-1, y-1, TOGGLE_CLICK);
            }
            //right up
            if (!(x == 0 || y == dimension - 1) && hasClickedField[x - 1][y + 1] == NOT_CLICKED) {
                setHasClickedField(x-1, y+1, TOGGLE_CLICK);
            }
            // down right
            if (!(x == dimension - 1 || y == dimension - 1) && hasClickedField[x + 1][y + 1] == NOT_CLICKED) {
                setHasClickedField(x+1, y+1, TOGGLE_CLICK);
            }
            // left down
            if (!(y == 0 || x == dimension - 1) && hasClickedField[x + 1][y - 1] == NOT_CLICKED) {
                setHasClickedField(x+1, y-1, TOGGLE_CLICK);
            }
        }
    }

    public short getMineFieldModel(int x, int y) {
        return mineFieldModel[x][y];
    }

    public short getHasClickedField(int x, int y) {
        return hasClickedField[x][y];
    }

    public int currentToggle() {
        return currentToggle;
    }

    public void switchToggle() {
        if (currentToggle == TOGGLE_CLICK) {
            currentToggle = TOGGLE_FLAG;
        }
        else {
            currentToggle = TOGGLE_CLICK;
        }
    }

    private void generateRandomNumber() {
        randomNumX = new Random(System.currentTimeMillis()).nextInt(dimension);
        randomNumY = new Random(System.currentTimeMillis()).nextInt(dimension);
    }

    public void setDimension(int number) {
        dimension = number;
    }
    public void setBombs(int number) {
        numberOfBombs = (short) number;
    }

    public void restartGame() {
        mineFieldModel = new short[dimension][dimension];
        hasClickedField = new short[dimension][dimension];
        for (int x = 0; x < dimension; x++) {
            for (int y = 0; y < dimension; y++) {
                mineFieldModel[x][y] = EMPTY;
            }
        }
        populateBombs();
        populateMineFieldModel();
        populateHasClickedField();
        populateMineFieldModel();
        bombsFound = 0;
    }


}
