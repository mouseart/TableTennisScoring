package mouseart.com.github.tabletennisscoring;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import mouseart.com.github.tabletennisscoring.data.GameContract;
import mouseart.com.github.tabletennisscoring.data.GameDao;
import mouseart.com.github.tabletennisscoring.data.GameDbHelper;
import mouseart.com.github.tabletennisscoring.data.GameListAdapter;
import mouseart.com.github.tabletennisscoring.data.GameLogContract;
import mouseart.com.github.tabletennisscoring.data.GameLogDao;
import mouseart.com.github.tabletennisscoring.data.GameLogDbHelper;

/**
 * StartNewGameActivity class
 * 开始一场新的比赛
 *
 * @author Leon Feng
 * @date 2017/11/19
 */
public class StartNewGameActivity extends AppCompatActivity {

    //当前是第几局
    public static int gameNumber = 1;

    //TeamA的总分
    public static int scoreAllA = 0;
    //TeamB的总分
    public static int scoreAllB = 0;

    //TeamA的得分
    public static int scoreTeamA = 0;
    //TeamB的得分
    public static int scoreTeamB = 0;

    //决定发球方，t-TeamA，f-TeamB，暂时默认TeamA发球
    public boolean servingSideTeamA = true;

    //回合数量累计
    public int rounds = 1;

    //回合上限初始默认每回合11局；
    public static int scoreLimit = 11;

    /**
     * EditText field to enter the TeamRed's name
     */
    private EditText mTeamRedNameEditText;

    /**
     * EditText field to enter the TeamBlue's name
     */
    private EditText mTeamBlueNameEditText;

    /**
     * Spinner field to enter the game's sets
     */
    private Spinner mGameSetsSpinner;

    /**
     * 临时设置：TeamA的playerId=0；TeamB的playerId=1
     */
    private int mTeamAplayer1Id = 0;
    private int mTeamAplayer2Id = 1;
    private int mTeamBplayer1Id = 2;
    private int mTeamBplayer2Id = 3;

    //初始化比赛得分；
    String gameScore = gameNumber + "-" + scoreTeamA + ":" + scoreTeamB + "-" + scoreAllA + ":" + scoreAllB;

    /**
     * 临时设置：singlesDoubles = 1,暂时只支持单打，加入双打功能后替换
     */
    private int mSinglesDoubles = GameContract.GameEntry.SINGLESDOUBLES_SINGLE;

    /**
     * 设置赛制的默认值为:5
     * 3 三局两胜, 5 五局三胜, 7 七局四胜
     */
    private int mGameSets = GameContract.GameEntry.GAMESETS_5;

    GameDao mGameDao = new GameDao(this);
    GameLogDao mGameLogDao = new GameLogDao(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_new_game);

        // Find all relevant views that we will need to read user input from
        mTeamRedNameEditText = (EditText) findViewById(R.id.edit_teamA_name);
        mTeamBlueNameEditText = (EditText) findViewById(R.id.edit_teamB_name);

        mGameSetsSpinner = (Spinner) findViewById(R.id.spinner_gameSets);

        setupSpinner();

        // 点击开始比赛按钮
        final Button buttonStartNewGame = findViewById(R.id.start_game_button);
        buttonStartNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Code here executes on main thread after user presses button

                //创建新的一场比赛数据写入game表
                mGameDao.insertNewGame(mTeamAplayer1Id, mTeamBplayer1Id, mSinglesDoubles, mGameSets);
                displayDatabaseInfo();

                //写日志

                int gameId = mGameDao.returnLastId();
                int eventType = mGameSets;

                mGameLogDao.insertData(gameId, eventType, gameScore);


                //跳转到下一个Activity
                Intent intent = new Intent(StartNewGameActivity.this, ScoringActivity.class);
                startActivity(intent);



            }
        });
    }

    /**
     * 设置下拉菜单，允许用户选择比赛赛制
     */
    private void setupSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter gameSetsSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_gameSets_options, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        gameSetsSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mGameSetsSpinner.setAdapter(gameSetsSpinnerAdapter);

        // Set the integer mSelected to the constant values
        mGameSetsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.gameSet_3))) {
                        mGameSets = GameContract.GameEntry.GAMESETS_3; // 三局两胜
                    } else if (selection.equals(getString(R.string.gameSet_5))) {
                        mGameSets = GameContract.GameEntry.GAMESETS_5; // 五局三胜
                    } else {
                        mGameSets = GameContract.GameEntry.GAMESETS_7; // 七局四胜
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mGameSets = GameContract.GameEntry.GAMESETS_5; // 五局三胜
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_start_new_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
/*            case R.id.action_save:
                // Do nothing for now

                insertPet();
                finish();

                return true;*/
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                // Do nothing for now
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // Navigate back to parent activity (CatalogActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 用来显示数据库写入信息的临时辅助方法
     * 显示game表写入的内容志
     */
    private void displayDatabaseInfo() {
        // 进入数据库，实例化类SQLiteOpenHelper。
        // 并传递上下文，即当前活动。
        GameDbHelper mDbHelper = new GameDbHelper(this);

        // 创建或打开一个要读取内容的数据库
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // 执行类似 "SELECT * FROM gameLog" 的SQL语句
        // 获得表 gameLog 的所有内容
        /*Cursor cursor = db.rawQuery("SELECT * FROM " + GameContract.GameEntry.TABLE_NAME, null);*/
        Cursor cursor = db.query(
                GameContract.GameEntry.TABLE_NAME,
                null,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );

        try {
            // 显示表的行数
            TextView displayView = (TextView) findViewById(R.id.game_database_view_line);
            displayView.setText("game表有: " + cursor.getCount() + " 行 ");

            int gameIdColumnIndex = cursor.getColumnIndex(GameContract.GameEntry.COLUMN_GAME_ID);
            int gameTitleColumnIndex = cursor.getColumnIndex(GameContract.GameEntry.COLUMN_GAME_GAMETITLE);
            int gameStartTimeColumnIndex = cursor.getColumnIndex(GameContract.GameEntry.COLUMN_GAME_GAMESTARTTIME);
            int gameEndTimeColumnIndex = cursor.getColumnIndex(GameContract.GameEntry.COLUMN_GAME_GAMEENDTIME);
            int teamAPlayerId1ColumnIndex = cursor.getColumnIndex(GameContract.GameEntry.COLUMN_GAME_TEAMAPLAYERID1);
            int teamAPlayerId2ColumnIndex = cursor.getColumnIndex(GameContract.GameEntry.COLUMN_GAME_TEAMAPLAYERID2);
            int teamBPlayerId1ColumnIndex = cursor.getColumnIndex(GameContract.GameEntry.COLUMN_GAME_TEAMBPLAYERID1);
            int teamBPlayerId2ColumnIndex = cursor.getColumnIndex(GameContract.GameEntry.COLUMN_GAME_TEAMBPLAYERID2);
            int singlesDoublesColumnIndex = cursor.getColumnIndex(GameContract.GameEntry.COLUMN_GAME_SINGLESDOUBLES);
            int gameSetsColumnIndex = cursor.getColumnIndex(GameContract.GameEntry.COLUMN_GAME_GAMESETS);
            int gameScoreABColumnIndex = cursor.getColumnIndex(GameContract.GameEntry.COLUMN_GAME_GAMESCOREAB);
            int aggregateScoreColumnIndex = cursor.getColumnIndex(GameContract.GameEntry.COLUMN_GAME_AGGREGATESCORE);

            cursor.moveToLast();
            int currentID = cursor.getInt(gameIdColumnIndex);
            String currentGameTitle = cursor.getString(gameTitleColumnIndex);
            int currentGameStartTime = cursor.getInt(gameStartTimeColumnIndex);
            int currentGameEndTime = cursor.getInt(gameEndTimeColumnIndex);
            int currentTeamAPlayerId1 = cursor.getInt(teamAPlayerId1ColumnIndex);
            int currentTeamAPlayerId2 = cursor.getInt(teamAPlayerId2ColumnIndex);
            int currentTeamBPlayerId1 = cursor.getInt(teamBPlayerId1ColumnIndex);
            int currentTeamBPlayerId2 = cursor.getInt(teamBPlayerId2ColumnIndex);
            int currentSinglesDoubles = cursor.getInt(singlesDoublesColumnIndex);
            int currentGameSets = cursor.getInt(gameSetsColumnIndex);
            String currentGameScoreAB = cursor.getString(gameScoreABColumnIndex);
            String currentAggregateScore = cursor.getString(aggregateScoreColumnIndex);

            displayView.append(currentID + " - " +
                    currentGameTitle + " - " +
                    currentGameStartTime + " - " +
                    currentGameEndTime + " - " +
                    currentTeamAPlayerId1 + " - " +
                    currentTeamAPlayerId2 + " - " +
                    currentTeamBPlayerId1 + " - " +
                    currentTeamBPlayerId2 + " - " +
                    currentSinglesDoubles + " - " +
                    currentGameSets + " - " +
                    currentGameScoreAB + " - " +
                    currentAggregateScore + "\n");


        } finally

        {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }


}

