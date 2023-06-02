package com.example.betano;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RecordFragment extends AppCompatActivity {
    private LinearLayout scoresLayout;
    private ScoreDao scoreDao;
    private static final String PREFS_NAME = "MyPrefs";
    private CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        scoresLayout = findViewById(R.id.scores_layout);
        scoreDao = AppDatabase.getInstance(getApplicationContext()).scoreDao();
        compositeDisposable = new CompositeDisposable();

        displayScores();

        LinearLayout menuLayout = findViewById(R.id.menu_layout);
        menuLayout.setOnClickListener(v -> onBackPressed());
    }

    private void displayScores() {
        Flowable<List<Score>> scoreFlowable = scoreDao.getAllScores()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        compositeDisposable.add(scoreFlowable.subscribe(this::updateScoresLayout, Throwable::printStackTrace));
    }

    private void updateScoresLayout(List<Score> scores) {
        List<Integer> scoreValues = new ArrayList<>();
        for (Score score : scores) {
            scoreValues.add(score.getValue());
        }

        scoreValues.add(getIntent().getIntExtra("score", 0)); // Add the current game score

        // Sort the scores in descending order
        scoreValues.sort(Collections.reverseOrder());

        scoresLayout.removeAllViews(); // Clear existing scores layout

        int rank = 1;
        for (int i = 0; i < scoreValues.size(); i++) {
            int currentScore = scoreValues.get(i);

            if (i > 0 && currentScore == scoreValues.get(i - 1)) {
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
        rankTextView.setTextAppearance(android.R.style.TextAppearance_Medium);
        rankTextView.setTextSize(32); // Increase text size
        rankTextView.setTextColor(ContextCompat.getColor(this, android.R.color.white));
        rankTextView.setTypeface(null, Typeface.BOLD_ITALIC);

        TextView scoreTextView = new TextView(this);
        scoreTextView.setLayoutParams(params);
        scoreTextView.setTextAppearance(android.R.style.TextAppearance_Medium);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }
}
