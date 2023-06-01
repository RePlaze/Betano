package com.example.betano;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class RecordActivity extends AppCompatActivity {
    private LinearLayout scoresLayout;
    private SharedPreferences preferences;
    private static final String PREFS_NAME = "MyPrefs";
    private int score; // Game score

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        scoresLayout = findViewById(R.id.scores_layout);
        preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        // Example scores
        preferences.edit().putInt("Score1", 135).apply();
        preferences.edit().putInt("Score2", 87).apply();
        preferences.edit().putInt("Score3", 17).apply();

        score = getIntent().getIntExtra("score", 0); // Get the game score from the intent

        displayScores();

        LinearLayout menuLayout = findViewById(R.id.menu_layout);
        menuLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMainActivity();
            }
        });
    }

    private void displayScores() {
        Map<String, ?> scoreMap = preferences.getAll();

        List<Integer> scores = new ArrayList<>();
        for (Map.Entry<String, ?> entry : scoreMap.entrySet()) {
            if (entry.getValue() instanceof Integer) {
                scores.add((Integer) entry.getValue());
            }
        }

        scores.add(score); // Add the current game score

        // Sort the scores in descending order
        Collections.sort(scores, Collections.reverseOrder());

        scoresLayout.removeAllViews(); // Clear existing scores layout

        int rank = 1;
        for (int i = 0; i < scores.size(); i++) {
            int currentScore = scores.get(i);

            if (i > 0 && currentScore == scores.get(i - 1)) {
                // Skip adding the score if it is the same as the previous one
                continue;
            }

            addScoreToLayout(rank, currentScore);
            rank++;
        }
    }

    private void addScoreToLayout(int rank, int score) {
        TextView textView = new TextView(this);
        textView.setTextAppearance(this, android.R.style.TextAppearance_Medium); // Apply default text style
        textView.setText("Rank " + rank + ": " + score);
        scoresLayout.addView(textView);
    }

    private void goToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
