package mouseart.com.github.tabletennisscoring;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Date;

import mouseart.com.github.tabletennisscoring.data.GameLogContract;
import mouseart.com.github.tabletennisscoring.data.GameLogDbHelper;

import static android.text.style.TtsSpan.GENDER_MALE;

/**
 * ScoringActivity class
 * 计分界面
 * @author Leon Feng
 * @date 2017/11/16
 */
public class ScoringActivity extends AppCompatActivity {

    //当前是第几局
    public int gameNumber = 1;

    //TeamA的总分
    public int scoreAllA = 0;
    //TeamB的总分
    public int scoreAllB = 0;

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

    // 获得开局时间
    long timecurrentTimeMillis = System.currentTimeMillis();

    //游戏id暂时设置为1
    int gameId = 1;

    int eventType = 0;

    //初始化比赛得分；
    String gameScore=gameNumber+"-"+scoreTeamA+":"+scoreTeamB+"-"+scoreAllA+":"+scoreAllB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoring);

        //写入开局日志
        if (servingSideTeamA) {
            //TeamA发球开局
            eventType = 111;
        } else {
            //TeamB发球开局
            eventType=121;
        }
        insertData(gameId,eventType,gameScore);
        displayDatabaseInfo();

        displayForTeamA(scoreTeamA);
        displayForTeamB(scoreTeamB);
        displayForTeamStatus(scoreTeamA, scoreTeamB, rounds);
    }

    /**
     * 显示团队名字和哪方发球的状态
     */
    private void displayForTeamStatus(int scoreA, int scoreB, int roundsNumber) {
        if ((scoreA <= scoreLimit - 1) && (scoreB <= scoreLimit - 1)) {

            if (((roundsNumber % 4) == 1) || ((roundsNumber % 4) == 2)) {
                servingSideTeamA = true;

            } else {
                servingSideTeamA = false;
            }

        } else {
            servingSideTeamA = !servingSideTeamA;
        }
        if (servingSideTeamA) {
            displayForTeamViewA("TeamAName 发球");
            displayForTeamViewB("TeamBName ");

            //写入发球日志
            eventType=211;
            insertData(gameId,eventType,gameScore);
            displayDatabaseInfo();
        } else {
            displayForTeamViewA("TeamAName");
            displayForTeamViewB("TeamBName 发球");

            //写入发球日志
            eventType=221;
            insertData(gameId,eventType,gameScore);
            displayDatabaseInfo();
        }

    }


    /**
     * 显示A队得分
     */
    public void displayForTeamA(int score) {
        final Button scoreView = (Button) findViewById(R.id.team_a_score);
        String str = String.valueOf(score);
        scoreView.setText(str);

    }

    /**
     * 显示B队得分
     */
    public void displayForTeamB(int score) {
        final Button scoreView = (Button) findViewById(R.id.team_b_score);
        String str = String.valueOf(score);
        scoreView.setText(str);

    }

    /**
     * 显示A队团队状态
     */
    public void displayForTeamViewA(String messageA) {
        final TextView teamViewA = (TextView) findViewById(R.id.team_a_nameView);
        teamViewA.setText(messageA);

    }

    /**
     * 显示B队团队状态
     */
    public void displayForTeamViewB(String messageB) {
        final TextView teamViewA = (TextView) findViewById(R.id.team_b_nameView);
        teamViewA.setText(messageB);

    }


    /**
     * 对A队加分；
     * 并根据得分，调用方法进行输赢判断
     */
    public void addOneForTeamA(View v) {
        scoreTeamA = scoreTeamA + 1;

        //写入TeamA得分日志
        eventType=311;
        insertData(gameId,eventType,gameScore);
        displayDatabaseInfo();

        switch (tableTennisScoreJudgment(scoreTeamA, scoreTeamB)) {
            case 0:
                System.out.println("A+1比赛继续");
                displayForTeamA(scoreTeamA);
                rounds++;
                displayForTeamStatus(scoreTeamA, scoreTeamB, rounds);
                break;
            case 1:
                System.out.println("A获得本回合胜利！");
                displayForTeamA(scoreTeamA);
                rounds++;
                displayForTeamViewA("TeamAName 获得本回合胜利！");
                displayForTeamViewB("TeamBName ");
                //写入TeamA获得本回合胜利日志
                eventType=411;
                insertData(gameId,eventType,gameScore);
                displayDatabaseInfo();

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
        scoreTeamB = scoreTeamB + 1;
        //写入TeamB得分日志
        eventType=321;
        insertData(gameId,eventType,gameScore);
        displayDatabaseInfo();
        switch (tableTennisScoreJudgment(scoreTeamA, scoreTeamB)) {
            case 0:
                System.out.println("B+1比赛继续");
                displayForTeamB(scoreTeamB);
                rounds++;
                displayForTeamStatus(scoreTeamA, scoreTeamB, rounds);
                break;
            case 2:
                System.out.println("B获得本回合胜利！");
                displayForTeamB(scoreTeamB);
                rounds++;
                displayForTeamViewA("TeamAName ");
                displayForTeamViewB("TeamBName 获得本回合胜利！");
                //写入TeamB获得本回合胜利日志
                eventType=421;
                insertData(gameId,eventType,gameScore);
                displayDatabaseInfo();

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
        if ((scoreA <= scoreLimit - 1) && (scoreB <= scoreLimit - 1)) {
            result = 0;
        } else if ((scoreA == scoreLimit) && (scoreB < scoreLimit - 1)) {
            result = 1;
        } else if ((scoreA < scoreLimit - 1) && (scoreB == scoreLimit)) {
            result = 2;
        } else if ((scoreA >= scoreLimit - 1) && (scoreB >= scoreLimit - 1) && (Math.abs(scoreA - scoreB) < 2)) {
            result = 0;
        } else if (scoreA > scoreB) {
            result = 1;
        } else {
            result = 2;
        }
        return result;
    }

    /**
     * 向gameLog写入日志
     */
    private void insertData(int gameId,int eventType, String gameScore) {

        GameLogDbHelper mDbHelper = new GameLogDbHelper(this);
        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(GameLogContract.GameLogEntry.COLUMN_GAMELOG_GAMEID, gameId);
        values.put(GameLogContract.GameLogEntry.COLUMN_GAMELOG_EVENTTIME, System.currentTimeMillis());
        values.put(GameLogContract.GameLogEntry.COLUMN_GAMELOG_EVENTTYPE, eventType);
        values.put(GameLogContract.GameLogEntry.COLUMN_GAMELOG_GAMESCORE, gameScore);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(GameLogContract.GameLogEntry.TABLE_NAME, null, values);

    }



    /**
     * 用来显示数据库写入信息的临时辅助方法
     * 显示gameLog写入的日志
     */
    private void displayDatabaseInfo() {
        // 进入数据库，实例化类SQLiteOpenHelper。
        // 并传递上下文，即当前活动。
        GameLogDbHelper mDbHelper = new GameLogDbHelper(this);

        // 创建或打开一个要读取内容的数据库
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // 执行类似 "SELECT * FROM gameLog" 的SQL语句
        // 获得表 gameLog 的所有内容
        /*Cursor cursor = db.rawQuery("SELECT * FROM " + GameLogContract.GameLogEntry.TABLE_NAME, null);*/
        Cursor cursor = db.query(
                GameLogContract.GameLogEntry.TABLE_NAME,
                null,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );

        try {
            // 显示表的行数
            TextView displayView = (TextView) findViewById(R.id.database_view_line);
            displayView.setText("gameLog表有: " + cursor.getCount() + " 行 ");

            int gameLogIdColumnIndex = cursor.getColumnIndex(GameLogContract.GameLogEntry.COLUMN_GAMELOG_ID);
            int gameIdColumnIndex = cursor.getColumnIndex(GameLogContract.GameLogEntry.COLUMN_GAMELOG_GAMEID);
            int eventTimeColumnIndex = cursor.getColumnIndex(GameLogContract.GameLogEntry.COLUMN_GAMELOG_EVENTTIME);
            int eventTypeColumnIndex = cursor.getColumnIndex(GameLogContract.GameLogEntry.COLUMN_GAMELOG_EVENTTYPE);
            int gameSoreColumnIndex = cursor.getColumnIndex(GameLogContract.GameLogEntry.COLUMN_GAMELOG_GAMESCORE);

            cursor.moveToLast();
            int currentID = cursor.getInt(gameLogIdColumnIndex);
            int currentGameId = cursor.getInt(gameIdColumnIndex);
            int currentEventTime = cursor.getInt(eventTimeColumnIndex);
            int currentEventType = cursor.getInt(eventTypeColumnIndex);
            String currentGameSore = cursor.getString(gameSoreColumnIndex);

            displayView.append(currentID + " - " +
                    currentGameId + " - " +
                    currentEventTime + " - " +
                    currentEventType + " - " +
                    currentGameSore + "\n");


        } finally

        {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }


}
