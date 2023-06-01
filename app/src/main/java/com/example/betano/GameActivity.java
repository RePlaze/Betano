package com.example.betano;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity implements View.OnTouchListener {
    private int score;
    private TextView scoreTextView;
    private ImageView soccerBallImageView;

    private SharedPreferences preferences;

    private static final String PREFS_NAME = "MyPrefs";
    private static final String GAME_SCORE_KEY = "GameScore";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        scoreTextView = findViewById(R.id.score_text_view);
        soccerBallImageView = findViewById(R.id.soccer_ball_image_view);

        preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        // Start a new game with a score of 0
        score = 0;
        scoreTextView.setText(String.valueOf(score));

        soccerBallImageView.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int[] ballLocation = new int[2];
            soccerBallImageView.getLocationOnScreen(ballLocation);
            int ballX = ballLocation[0];
            int ballY = ballLocation[1];

            int touchX = (int) event.getRawX();
            int touchY = (int) event.getRawY();

            int ballCenterX = ballX + soccerBallImageView.getWidth() / 2;
            int ballCenterY = ballY + soccerBallImageView.getHeight() / 2;
            double distance = Math.sqrt(Math.pow(touchX - ballCenterX, 2) + Math.pow(touchY - ballCenterY, 2));

            int threshold = 200;

            if (distance < threshold) {
                increaseScore();
                return true;
            } else {
                endGame();
                return false;
            }
        }
        return false;
    }

    private void increaseScore() {
        score++;
        scoreTextView.setText(String.valueOf(score));
    }

    private void endGame() {
        saveScoreToPreferences();

        // Display a toast message to indicate a miss
        Toast.makeText(this, "Missed!", Toast.LENGTH_SHORT).show();

        // Start the ResultActivity and pass the score
        Intent intent = new Intent(GameActivity.this, ResultActivity.class);
        intent.putExtra("score", score);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveScoreToPreferences();
    }

    private void saveScoreToPreferences() {
        preferences.edit().putInt(GAME_SCORE_KEY, score).apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
        score = preferences.getInt(GAME_SCORE_KEY, 0);
        scoreTextView.setText(String.valueOf(score));
    }

    public void showRecordActivity(View view) {
        // Save the score and navigate to the RecordActivity
        saveScoreToPreferences();
        Intent intent = new Intent(GameActivity.this, RecordActivity.class);
        intent.putExtra("score", score);
        startActivity(intent);
        finish();
    }
}
