package com.example.betano;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RelativeLayout startGameButton = findViewById(R.id.start_game_layout);
        RelativeLayout recordButton = findViewById(R.id.record_layout);
        RelativeLayout exitButton = findViewById(R.id.exit_layout);

        startGameButton.setOnClickListener(this);
        recordButton.setOnClickListener(this);
        exitButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_game_layout:
                // Start Game button clicked
                startNewGame();
                break;
            case R.id.record_layout:
                // Record button clicked
                Intent recordIntent = new Intent(MainActivity.this, RecordActivity.class);
                startActivity(recordIntent);
                break;
            case R.id.exit_layout:
                // Exit button clicked
                finish();
                break;
        }
    }

    private void startNewGame() {
        // Clear the game score in shared preferences
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        preferences.edit().remove("GameScore").apply();

        // Start the game activity
        Intent gameIntent = new Intent(MainActivity.this, GameActivity.class);
        startActivity(gameIntent);
    }
}
