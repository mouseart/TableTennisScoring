package mouseart.com.github.tabletennisscoring;

import android.content.ContentValues;
import android.content.Intent;
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
 * @author Leon Feng
 * @date 2017/11/19
 */
public class StartNewGameActivity extends AppCompatActivity {

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
    String gameScore=gameNumber+"-"+scoreTeamA+":"+scoreTeamB+"-"+scoreAllA+":"+scoreAllB;

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
                mGameDao.insertNewGame(mTeamAplayer1Id,mTeamBplayer1Id,mSinglesDoubles,mGameSets);

                //跳转到下一个Activity
                Intent intent = new Intent(StartNewGameActivity.this, ScoringActivity.class);
                startActivity(intent);

                //写日志

                int gameId =
                int eventType=mGameSets;
                GameListAdapter.git();
                mGameLogDao.insertData(gameId,eventType,gameScore);

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
                mGameSets =  GameContract.GameEntry.GAMESETS_5; // 五局三胜
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


    }

