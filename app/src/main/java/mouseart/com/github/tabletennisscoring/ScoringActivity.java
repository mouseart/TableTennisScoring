package mouseart.com.github.tabletennisscoring;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ScoringActivity extends AppCompatActivity {

    //TeamA的得分
    public int scoreTeamA = 0;
    //TeamB的得分
    public int scoreTeamB = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoring);
        displayForTeamA(scoreTeamA);
        displayForTeamB(scoreTeamB);
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
     * 对A队加分；
     * 并根据得分，调用方法进行输赢判断
     */
    public void addOneForTeamA(View v) {
        scoreTeamA = scoreTeamA +1;
        switch (tableTennisScoreJudgment(scoreTeamA,scoreTeamB,11)) {
            case 0:
                System.out.println("A+1比赛继续");
                displayForTeamA(scoreTeamA);
                break;
            case 1:
                System.out.println("A获得本回合胜利！");
                displayForTeamA(scoreTeamA);
            default:
                System.out.println("返回结果错误！");
                break;
        }
    }

    public void addOneForTeamB(View v) {
        scoreTeamB = scoreTeamB +1;
        switch (tableTennisScoreJudgment(scoreTeamA,scoreTeamB,11)) {
            case 0:
                System.out.println("B+1比赛继续");
                displayForTeamB(scoreTeamB);
                break;
            case 2:
                System.out.println("B获得本回合胜利！");
                displayForTeamB(scoreTeamB);
            default:
                System.out.println("返回结果错误！");
                break;
        }
    }



    /**
     * 排球比赛得分胜负判定
     * 前2个参数为2个选手的得分；
     * 第3个参数，通常为11，也可传入21修改为21球的规则（或任意球的规则）；
     * 返回参数：0-继续比赛；1-A本局获胜；2-B本局获胜；
     */
    private static int tableTennisScoreJudgment(int scoreA, int scoreB, int scoreLimit) {
        int result;
        if ((scoreA <= scoreLimit-1) && (scoreB <= scoreLimit-1)) {
            result=0;
        } else if ((scoreA == scoreLimit) && (scoreB <= scoreLimit-1)) {
            result=1;
        } else if ((scoreA < scoreLimit-1) && (scoreB == scoreLimit)) {
            result=2;
        } else if ((scoreA >= scoreLimit-1) && (scoreB >= scoreLimit-1) && (Math.abs(scoreA-scoreB) < 2)) {
            result=0;
        } else if (scoreA > scoreB){
            result=1;
        }else {
            result=2;
        }
        return result;
    }

    /**
     * 显示队得分
     */
    public void displayForTeamB(int score) {
        final Button scoreView = (Button)findViewById(R.id.team_b_score);
        String str= String.valueOf(score);
        scoreView.setText(str);

    }


}
