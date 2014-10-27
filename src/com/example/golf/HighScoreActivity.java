package com.example.golf;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * This activity is run when a user clicks the High Scores button from the main
 * activity. It displays a list of the 5 lowest scores from previously played
 * games of Golf.
 * @author 
 *
 */
public class HighScoreActivity extends Activity implements View.OnClickListener {

	private TextView score1;
	private TextView score2;
	private TextView score3;
	private TextView score4;
	private TextView score5;
	private ImageView highscore;
	private Button clearButton;
	public static final String PREFS_NAME = "MyPrefsFile";
	public SharedPreferences scorePrefs;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.high_score);
		
		score1 = (TextView) findViewById(R.id.highScoreName1);
		score2 = (TextView) findViewById(R.id.highScoreName2);
		score3 = (TextView) findViewById(R.id.highScoreName3);
		score4 = (TextView) findViewById(R.id.highScoreName4);
		score5 = (TextView) findViewById(R.id.highScoreName5);
		highscore = (ImageView) findViewById(R.id.highscore_image);
		
		highscore.bringToFront();
		score1.bringToFront();
		score2.bringToFront();
		score3.bringToFront();
		score4.bringToFront();
		score5.bringToFront();
		
		TextView[] scoreViews = { score1, score2, score3, score4, score5 };

		clearButton = (Button) findViewById(R.id.clear_scores_button);
		clearButton.setOnClickListener(this);
		clearButton.setBackgroundResource(R.drawable.button_press);

		scorePrefs = getSharedPreferences(PREFS_NAME, 0);
		String[] list = { scorePrefs.getString("score1", "No Score"),
				scorePrefs.getString("score2", "No Score"),
				scorePrefs.getString("score3", "No Score"),
				scorePrefs.getString("score4", "No Score"),
				scorePrefs.getString("score5", "No Score") };

		for (int i = 0; i < scoreViews.length; i++) {
			if (list[i] == "") {
				scoreViews[i].setText("No Score");
			} else {
				scoreViews[i].setText(list[i]);
			}
		}

	}

	@Override
	public void onClick(View v) {

		String[] scoreKeys = { "score1", "score2", "score3", "score4", "score5" };
		SharedPreferences.Editor editor = scorePrefs.edit();

		TextView[] scoreViews = { score1, score2, score3, score4, score5 };
		for (int i = 0; i < scoreViews.length; i++) {
			scoreViews[i].setText("No Score");
			editor.putString(scoreKeys[i], "");
		}
		editor.commit();
	}

}
