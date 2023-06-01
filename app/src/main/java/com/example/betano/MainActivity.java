package com.example.betano;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String PREFS_NAME = "MyPrefs";
    private static final String TUTORIAL_SHOWN_KEY = "TutorialShown";

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

        // Check if the tutorial has been shown before
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean tutorialShown = preferences.getBoolean(TUTORIAL_SHOWN_KEY, false);
        if (!tutorialShown) {
            showTutorialDialog();
        }
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
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        preferences.edit().remove("GameScore").apply();

        // Start the game activity
        Intent gameIntent = new Intent(MainActivity.this, GameActivity.class);
        startActivity(gameIntent);
    }

    private void showTutorialDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Welcome to our app")
                .setMessage("Instructions:\n\n" +
                        "1. Fill the ball by touching it.\n" +
                        "2. Be careful not to touch the edges of the ball, or you will lose.\n" +
                        "3. Check the record tab for the best scores.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Mark the tutorial as shown
                        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putBoolean(TUTORIAL_SHOWN_KEY, true);
                        editor.apply();
                    }
                })
                .setCancelable(false)
                .show();
    }

}
