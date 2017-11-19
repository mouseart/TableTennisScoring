package mouseart.com.github.tabletennisscoring;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import mouseart.com.github.tabletennisscoring.data.GameContract;

/**
 * StartNewGame class
 * 开始一场新的比赛
 * @author Leon Feng
 * @date 2017/11/19
 */
public class StartNewGame extends AppCompatActivity {

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
     * 设置赛制的默认值为:5
     * 3 三局两胜, 5 五局三胜, 7 七局四胜
     */
    private int mGameSets = GameContract.GameEntry.GAMESETS_5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_new_game);

        // Find all relevant views that we will need to read user input from
        mTeamRedNameEditText = (EditText) findViewById(R.id.edit_teamA_name);
        mTeamBlueNameEditText = (EditText) findViewById(R.id.edit_teamB_name);

        mGameSetsSpinner = (Spinner) findViewById(R.id.spinner_gameSets);

        setupSpinner();
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

    /**
     * 向game表增加一场新的比赛
     */
    private void insertNewGame() {

    }


    }

