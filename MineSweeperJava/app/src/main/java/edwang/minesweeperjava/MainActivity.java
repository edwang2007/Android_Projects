package edwang.minesweeperjava;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import edwang.minesweeperjava.model.MinesweeperModel;
import edwang.minesweeperjava.view.MinesweeperView;

public class MainActivity extends AppCompatActivity {

    // private TextView tvStatus = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final MinesweeperView minesweeperView = (MinesweeperView) findViewById(R.id.MinesweeperView);

        final Button btnSwitchToggle = (Button) findViewById(R.id.btnToggle);
        final Button btnRestart = (Button) findViewById(R.id.btnRestart);


        final Button btnDimension = (Button) findViewById(R.id.btnDimension);
        final Button btnBombs = (Button) findViewById(R.id.btnBombs);
        final EditText etDimension = (EditText) findViewById(R.id.etDimension);
        final EditText etBombs = (EditText) findViewById(R.id.etBombs);


        final LinearLayout backgroundLayout = (LinearLayout) findViewById(R.id.backgroundLayout);

        btnSwitchToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // viewFace.setBackgroundResource(R.drawable.happy_face);
                minesweeperView.switchToggle();
                if (MinesweeperModel.getInstance().currentToggle() == MinesweeperModel.TOGGLE_CLICK) {
                    btnSwitchToggle.setText("Toggle to Flag");
                    Snackbar.make(backgroundLayout, R.string.try_a_field, Snackbar.LENGTH_SHORT).show();
                }
                else {
                    btnSwitchToggle.setText("Toggle to Click");
                    Snackbar.make(backgroundLayout, R.string.place_a_flag, Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                minesweeperView.restartGame();
            }
        });

        btnDimension.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(etDimension.getText().toString()) && !etDimension.getText().equals("0")
                        && Integer.parseInt(etDimension.getText().toString()) * Integer.parseInt(etDimension.getText().toString())  >= MinesweeperModel.numberOfBombs) {
                    MinesweeperModel.getInstance().setDimension(Integer.parseInt(etDimension.getText().toString()));
                    minesweeperView.setDimension();
                    minesweeperView.restartGame();
                }
                else {
                    etDimension.setError("Invalid input!");
                }
            }
        });

        btnBombs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(etBombs.getText().toString()) && !etBombs.getText().equals("0")
                        && MinesweeperModel.dimension * MinesweeperModel.dimension >= MinesweeperModel.numberOfBombs) {
                    MinesweeperModel.getInstance().setBombs(Integer.parseInt(etBombs.getText().toString()));
                    minesweeperView.restartGame();
                }
                else {
                    etBombs.setError("Invalid Input!");
                }
            }
        });

    }



}
