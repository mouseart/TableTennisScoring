package mouseart.com.github.tabletennisscoring;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * ScoringActivity class
 *
 * @author Leon Feng
 * @date 2017/11/16
 */
public class ScoringActivity extends AppCompatActivity {



    //TeamA的得分
    public int scoreTeamA = 0;
    //TeamB的得分
    public int scoreTeamB = 0;

    //决定发球方，t-TeamA，f-TeamB，暂时默认TeamA发球
    public boolean servingSideTeamA = true;
    //回合数量累计
    public int rounds = 1;

    //回合上限初始默认每回合11局；
    public static int scoreLimit = 11;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoring);
        displayForTeamA(scoreTeamA);
        displayForTeamB(scoreTeamB);
        displayForTeamStatus(scoreTeamA,scoreTeamB,rounds);
    }

    /**
     * 显示团队名字和哪方发球的状态
     */
    private void displayForTeamStatus(int scoreA, int scoreB, int roundsNumber) {
        if ((scoreA <= scoreLimit-1) && (scoreB <= scoreLimit-1)){

            if (((roundsNumber % 4) == 1) || ((roundsNumber % 4) == 2)) {
                servingSideTeamA = true;
                return;
            }

            else {
                servingSideTeamA = false;
            }

        } else {
            servingSideTeamA = !servingSideTeamA;
        }
        if (servingSideTeamA) {
            displayForTeamViewA("TeamAName 发球");
            displayForTeamViewB("TeamBName ");
        } else {
            displayForTeamViewA("TeamAName");
            displayForTeamViewB("TeamBName 发球");
        }

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
     * 显示B队得分
     */
    public void displayForTeamB(int score) {
        final Button scoreView = (Button)findViewById(R.id.team_b_score);
        String str= String.valueOf(score);
        scoreView.setText(str);

    }

    /**
     * 显示A队团队状态
     */
    public void displayForTeamViewA(String messageA) {
        final TextView teamViewA = (TextView)findViewById(R.id.team_a_nameView);
        teamViewA.setText(messageA);

    }

    /**
     * 显示B队团队状态
     */
    public void displayForTeamViewB(String messageB) {
        final TextView teamViewA = (TextView)findViewById(R.id.team_b_nameView);
        teamViewA.setText(messageB);

    }


    /**
     * 对A队加分；
     * 并根据得分，调用方法进行输赢判断
     */
    public void addOneForTeamA(View v) {
        scoreTeamA = scoreTeamA +1;
        switch (tableTennisScoreJudgment(scoreTeamA,scoreTeamB)) {
            case 0:
                System.out.println("A+1比赛继续");
                displayForTeamA(scoreTeamA);
                rounds ++;
                displayForTeamStatus(scoreTeamA,scoreTeamB,rounds);
                break;
            case 1:
                System.out.println("A获得本回合胜利！");
                displayForTeamA(scoreTeamA);
                rounds ++;
                displayForTeamViewA("TeamAName 获得本回合胜利！");
                displayForTeamViewB("TeamBName ");
            default:
                System.out.println("返回结果错误！");
                break;
        }
    }

    /**
     * 对A队加分；
     * 并根据得分，调用方法进行输赢判断
     */
    public void addOneForTeamB(View v) {
        scoreTeamB = scoreTeamB +1;
        switch (tableTennisScoreJudgment(scoreTeamA,scoreTeamB)) {
            case 0:
                System.out.println("B+1比赛继续");
                displayForTeamB(scoreTeamB);
                rounds ++;
                displayForTeamStatus(scoreTeamA,scoreTeamB,rounds);
                break;
            case 2:
                System.out.println("B获得本回合胜利！");
                displayForTeamB(scoreTeamB);
                rounds ++;
                displayForTeamViewA("TeamAName ");
                displayForTeamViewB("TeamBName 获得本回合胜利！");
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
    private static int tableTennisScoreJudgment(int scoreA, int scoreB) {
        int result;
        if ((scoreA <= scoreLimit-1) && (scoreB <= scoreLimit-1)) {
            result=0;
        } else if ((scoreA == scoreLimit) && (scoreB < scoreLimit-1)) {
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




}
