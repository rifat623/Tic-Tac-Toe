package com.example.tictactoe;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button[][] buttons = new Button[3][3];
    private boolean player1Turn = true;
    private int roundCount;

    private int player1Points;
    private int player2Points;

    private TextView textViewPlayer1 = (TextView) findViewById(R.id.text_view_p1);
    private TextView textViewPlayer2 = (TextView) findViewById(R.id.text_view_p2);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Reference text views
        textViewPlayer1 = findViewById(R.id.text_view_p1);
        textViewPlayer2 = findViewById(R.id.text_view_p2);

        // Reference grid buttons
        for (int i = 0; i < 3; i ++) {
            for (int j = 0 ; j < 3; j++) {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }

        // Reference reset button
        Button resetButton = findViewById(R.id.resetButton);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameReset();
            }
        });
    }

    @Override
    public void onClick(View view) {
        // Check of button pressed is empty or not
        if (!((Button) view).getText().toString().equals(""))
             return;
        // Fill grid with X or O
        if (player1Turn)
            ((Button) view).setText("X");
        else
            ((Button) view).setText("O");

        roundCount++;

        // Check for winner
        if (roundCount == 9)
            draw();
        else if (checkWin()) {
            if (player1Turn)
                player1Wins();
            else
                player2Wins();
        }
        else
            player1Turn = !player1Turn;
    }

    private boolean checkWin() {
        String[][] grid = new String[3][3];

        // Copy current grid into string array
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                grid[i][j] = buttons[i][j].getText().toString();
            }
        }

        // Check rows, columns, and diagonals and return true if winner is found
        for (int i = 0; i < 3; i++) {
            // Rows
            if (grid[i][0].equals(grid[i][1]) && grid[i][0].equals(grid[i][2])
                    && !grid[i][0].equals(""))
                return true;

            // Columns
            if (grid[0][i].equals(grid[1][i]) && grid[0][i].equals(grid[2][i])
                    && !grid[0][i].equals(""))
                return true;
        }

        // Diagonals
        if (grid[0][0].equals(grid[1][1]) && grid[0][0].equals(grid[2][2])
                && !grid[0][0].equals(""))
            return true;
        if (grid[0][2].equals(grid[1][1]) && grid[0][2].equals(grid[2][0])
                && !grid[0][2].equals(""))
            return true;

        // Return false if there are no winners
        return false;
    }

    //if both players draw
    private void draw() {
        Toast.makeText(this, "Draw!!", Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    //update point for player 1 and display message
    private void player1Wins() {
        player1Points++;
        Toast.makeText(this, "Player 1 wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }

    //update point for player 2 and display message
    private void player2Wins() {
        player2Points++;
        Toast.makeText(this, "Player 2 wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }

    private void updatePointsText(){
        textViewPlayer1.setText("Player 1: " + player1Points);
        textViewPlayer2.setText("Player 2: " + player2Points);
    }

    private void resetBoard(){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }

        roundCount = 0;
        player1Turn = true;

    }

    private void gameReset() {
        player1Points = 0;
        player2Points = 0;
        updatePointsText();
        resetBoard();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("roundCount", roundCount);
        outState.putInt("player1Points", player1Points);
        outState.putInt("player2Points", player2Points);
        outState.putBoolean("player1Turn", player1Turn);
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        roundCount = savedInstanceState.getInt("roundCount");
        player1Points = savedInstanceState.getInt("player1Points");
        player2Points = savedInstanceState.getInt("player2Points");
        player1Turn = savedInstanceState.getBoolean("player1Turn");
    }
}
