package com.example.betano;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
public class ResultActivity extends AppCompatActivity {
    private static final int DELAY_TIME = 2500; // 2 seconds delay

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        int totalScore = getIntent().getIntExtra("score", 0);

        TextView scoreTextView = findViewById(R.id.total_score_text_view);
        scoreTextView.setText("You Score: " + totalScore);

        // Delay and navigate to MainActivity
        new Handler().postDelayed(() -> {
            addScoreToRecord(totalScore);
            goToMainActivity();
        }, DELAY_TIME);
    }

    private void addScoreToRecord(int score) {
        // Save the score to the record
        String prefsName = "MyPrefs";
        SharedPreferences preferences = getSharedPreferences(prefsName, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        int currentScore = preferences.getInt("GameScore", 0);
        editor.putInt("GameScore", currentScore + score);
        editor.apply();
    }

    private void goToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
