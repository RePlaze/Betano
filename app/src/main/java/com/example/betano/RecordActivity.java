package com.example.betano;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        scoresLayout = findViewById(R.id.scores_layout);
        preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        displayScores();

        LinearLayout menuLayout = findViewById(R.id.menu_layout);
        menuLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
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

        scores.add(getIntent().getIntExtra("score", 0)); // Add the current game score

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
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        TextView rankTextView = new TextView(this);
        rankTextView.setLayoutParams(params);
        rankTextView.setTextAppearance(this, android.R.style.TextAppearance_Medium);
        rankTextView.setText(" " + rank + ". ");
        rankTextView.setTextSize(32); // Increase text size
        rankTextView.setTextColor(ContextCompat.getColor(this, android.R.color.white));
        rankTextView.setTypeface(null, Typeface.BOLD_ITALIC);

        TextView scoreTextView = new TextView(this);
        scoreTextView.setLayoutParams(params);
        scoreTextView.setTextAppearance(this, android.R.style.TextAppearance_Medium);
        scoreTextView.setText(String.valueOf(score));
        scoreTextView.setTextSize(32); // Increase text size
        scoreTextView.setTextColor(ContextCompat.getColor(this, android.R.color.white));
        scoreTextView.setTypeface(null, Typeface.BOLD_ITALIC);

        LinearLayout layout = new LinearLayout(this);
        layout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setGravity(Gravity.CENTER_VERTICAL); // Center align vertically
        layout.addView(rankTextView);
        layout.addView(scoreTextView);

        // Add left margin to rank text view
        LinearLayout.LayoutParams rankParams = (LinearLayout.LayoutParams) rankTextView.getLayoutParams();
        rankParams.setMarginStart(60);
        rankTextView.setLayoutParams(rankParams);

        scoresLayout.addView(layout);
    }



}
