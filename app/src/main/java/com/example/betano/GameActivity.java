package com.example.betano;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity implements View.OnTouchListener {
    private int score;
    private TextView scoreTextView;
    private ImageView soccerBallImageView;

    private SharedPreferences preferences;

    public static final String PREFS_NAME = "MyPrefs";
    public static final String GAME_SCORE_KEY = "Game1Score";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        scoreTextView = findViewById(R.id.score_text_view);
        soccerBallImageView = findViewById(R.id.soccer_ball_image_view);

        preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        score = 0;
        updateScoreText();

        // Set up the touch listener for the ball
        soccerBallImageView.setOnTouchListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveScoreToPreferences();
    }

    private void updateScoreText() {
        String scoreText = "Score: " + score;
        scoreTextView.setText(scoreText);
    }

    private void saveScoreToPreferences() {
        int highScore = preferences.getInt(GAME_SCORE_KEY, 0);
        if (score > highScore) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt(GAME_SCORE_KEY, score);
            editor.apply();
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            // Get the current position of the soccer ball
            int[] ballLocation = new int[2];
            soccerBallImageView.getLocationOnScreen(ballLocation);
            int ballX = ballLocation[0];
            int ballY = ballLocation[1];

            // Get the touch coordinates
            int touchX = (int) event.getRawX();
            int touchY = (int) event.getRawY();

            // Calculate the distance between the touch point and the ball center
            int ballCenterX = ballX + soccerBallImageView.getWidth() / 2;
            int ballCenterY = ballY + soccerBallImageView.getHeight() / 2;
            double distance = Math.sqrt(Math.pow(touchX - ballCenterX, 2) + Math.pow(touchY - ballCenterY, 2));

            // Define the threshold for a successful hit (adjust as needed)
            int threshold = 200;

            if (distance < threshold) {
                // Increment the score and update the text view
                score++;
                updateScoreText();
            } else {
                // Save the score and navigate to the ResultActivity
                saveScoreToPreferences();
                Intent intent = new Intent(GameActivity.this, ResultActivity.class);
                intent.putExtra("score", score);
                startActivity(intent);
                finish();
            }
        }

        return true;
    }
}
