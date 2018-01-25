package edwang.minesweeperjava.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import edwang.minesweeperjava.model.MinesweeperModel;

/**
 * Created by student on 13/06/2017.
 */

public class MinesweeperView extends View{

    private Paint paintLine = null;
    private Paint paintBg = null;
    private Paint paintUnclickedSpace = null;
    private Paint paintText = null;
    private static int dimension = MinesweeperModel.dimension;

    public MinesweeperView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        paintBg = new Paint();
        paintBg.setColor(Color.CYAN);
        paintBg.setStyle(Paint.Style.FILL);

        paintLine = new Paint();
        paintLine.setColor(Color.BLACK);
        paintLine.setStyle(Paint.Style.FILL);
        paintLine.setStrokeWidth(10);

        paintUnclickedSpace = new Paint();
        paintUnclickedSpace.setColor(Color.GRAY);
        paintUnclickedSpace.setStyle(Paint.Style.FILL);

        paintText = new Paint();
        paintText.setStyle(Paint.Style.FILL);
        paintText.setColor(Color.BLACK);
        paintText.setStrokeWidth(30);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    public  void setDimension() {
        dimension = MinesweeperModel.dimension;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(0, 0, getWidth(), getHeight(), paintBg);
        paintText.setTextSize(getWidth() / dimension);

        drawMineModelValues(canvas);
        drawUnclickedSquares(canvas);
        drawModelLines(canvas);

    }

    private void drawModelLines(Canvas canvas) {
        for (int x = 1; x < dimension; x++) {
            canvas.drawLine((getWidth() * x) / dimension, 0, (getWidth() * x) / dimension, getHeight(),  paintLine);
            canvas.drawLine(0, (getHeight() * x) / dimension, getWidth(), (getHeight() * x) / dimension, paintLine);
        }
    }

    private void drawUnclickedSquares(Canvas canvas) {
        for (int x = 0; x < dimension; x++) {
            for (int y = 0; y < dimension; y++) {
                // normal click
                if (MinesweeperModel.getInstance().getHasClickedField(x,y) == MinesweeperModel.NOT_CLICKED) {
                    canvas.drawRect((x * getWidth()) / dimension,
                            (y * getHeight()) / dimension,
                            ((x + 1) * getWidth()) / dimension,
                            ((y + 1) * getHeight()) / dimension, paintUnclickedSpace);
                }
                // flag click
                else if (MinesweeperModel.getInstance().getHasClickedField(x,y) == MinesweeperModel.TOGGLE_FLAG) {
                    canvas.drawRect((x * getWidth()) / dimension,
                            (y * getHeight()) / dimension,
                            ((x + 1) * getWidth()) / dimension,
                            ((y + 1) * getHeight()) / dimension, paintLine);
                }
            }
        }
    }

    private void drawMineModelValues(Canvas canvas) {
        for (int x = 0; x < dimension; x++) {
            for (int y = 0; y < dimension; y++) {
                int centerX = x * getWidth() / dimension ;
                int centerY = y * getHeight() / dimension + getHeight() / dimension;


                if (MinesweeperModel.getInstance().getMineFieldModel(x,y) == MinesweeperModel.BOMB) {
                    canvas.drawText("X", centerX, centerY, paintText);
                }
                else if (MinesweeperModel.getInstance().getMineFieldModel(x,y) == MinesweeperModel.EMPTY) {
                    canvas.drawText("", centerX, centerY, paintText);
                }
                else {
                    String spaceValue = MinesweeperModel.getInstance().getMineFieldModel(x, y) + "";
                    canvas.drawText(spaceValue, centerX, centerY, paintText);
                }
            }
        }
    }

    public void restartGame() {
        MinesweeperModel.getInstance().restartGame();
        invalidate();
    }

    public void switchToggle() {
        MinesweeperModel.getInstance().switchToggle();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_MOVE) {
//            boolean test1 = faceView.onTouchEvent(event);
        }

        if (event.getAction() == MotionEvent.ACTION_UP) {

         //   boolean test2 = faceView.onTouchEvent(event);

            int x = (int)event.getX() / (getWidth()/dimension);
            int y = (int)event.getY() / (getHeight()/dimension);

            if (MinesweeperModel.getInstance().getHasClickedField(x,y) == MinesweeperModel.NOT_CLICKED) {

                if (MinesweeperModel.getInstance().currentToggle() == MinesweeperModel.TOGGLE_CLICK) {
                    MinesweeperModel.getInstance().setHasClickedField(x, y, MinesweeperModel.TOGGLE_CLICK);


                } else if (MinesweeperModel.getInstance().currentToggle() == MinesweeperModel.TOGGLE_FLAG) {
                    MinesweeperModel.getInstance().setHasClickedField(x, y, MinesweeperModel.TOGGLE_FLAG);
                }
                String message = MinesweeperModel.getInstance().checkWinLoss(x, y);

                if (message != "") {
                    Snackbar.make(this, message, Snackbar.LENGTH_SHORT).show();
                }
            }
            invalidate();
        }
        return true;
    }
}
