package com.example.betano;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.material.card.MaterialCardView;

public class ResultActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        int totalScore = getIntent().getIntExtra("score", 0);

        TextView scoreTextView = findViewById(R.id.total_score_text_view);
        scoreTextView.setText(String.valueOf(totalScore));
    }
}
