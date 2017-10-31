package mouseart.com.github.tabletennisscoring;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ScoringActivity extends AppCompatActivity {


    public int scoreTeamA = 0;
    public int scoreTeamB = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoring);
        displayForTeamA(scoreTeamA);
    }

    /**
     * 显示A队得分
     */
    public void displayForTeamA(int score) {
        final Button scoreView = (Button)findViewById(R.id.team_a_score);
        String str= String.valueOf(score);
        scoreView.setText(str);

    }

    /**
     * A队按钮逻辑
     */
    public void addOneForTeamA(View v) {

        if (scoreTeamA <11 )
            scoreTeamA = scoreTeamA + 1;
        displayForTeamA(scoreTeamA);
    }

    /**
     * 显示队得分
     */
    public void displayForTeamB(int score) {
        final Button scoreView = (Button)findViewById(R.id.team_b_score);
        String str= String.valueOf(score);
        scoreView.setText(str);

    }

    /**
     * 队按钮逻辑
     */
    public void addOneForTeamB(View v) {

        if (scoreTeamB <11 )
            scoreTeamB = scoreTeamB + 1;
        displayForTeamB(scoreTeamB);
    }

}
