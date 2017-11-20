package mouseart.com.github.tabletennisscoring.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static mouseart.com.github.tabletennisscoring.data.GameContract.GameEntry.GAMESETS_5;
import static mouseart.com.github.tabletennisscoring.data.GameContract.GameEntry.SINGLESDOUBLES_SINGLE;
import static mouseart.com.github.tabletennisscoring.data.GameLogDbHelper.DATABASE_NAME;
import static mouseart.com.github.tabletennisscoring.data.GameLogDbHelper.DATABASE_VERSION;

/**
 * Created by mouse on 2017/11/15.
 */

public class GameDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "TableTennis.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String PRIMARY_KEY = " PRIMARY KEY";
    private static final String AUTOINCREMENT = " AUTOINCREMENT";
    private static final String NOT_NULL = " NOT NULL";
    private static final String DEFAULT = " DEFAULT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + GameContract.GameEntry.TABLE_NAME + " (" +
                    GameContract.GameEntry.COLUMN_GAME_ID + INTEGER_TYPE + PRIMARY_KEY + AUTOINCREMENT + COMMA_SEP +
                    GameContract.GameEntry.COLUMN_GAME_GAMETITLE + TEXT_TYPE + NOT_NULL + COMMA_SEP +
                    GameContract.GameEntry.COLUMN_GAME_GAMESTARTTIME + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
                    GameContract.GameEntry.COLUMN_GAME_GAMEENDTIME + INTEGER_TYPE + COMMA_SEP +
                    GameContract.GameEntry.COLUMN_GAME_TEAMAPLAYERID1 + INTEGER_TYPE + COMMA_SEP +
                    GameContract.GameEntry.COLUMN_GAME_TEAMAPLAYERID2 + INTEGER_TYPE + COMMA_SEP +
                    GameContract.GameEntry.COLUMN_GAME_TEAMBPLAYERID1 + INTEGER_TYPE + COMMA_SEP +
                    GameContract.GameEntry.COLUMN_GAME_TEAMBPLAYERID2 + INTEGER_TYPE + COMMA_SEP +
                    GameContract.GameEntry.COLUMN_GAME_SINGLESDOUBLES + INTEGER_TYPE + NOT_NULL + DEFAULT + " " + SINGLESDOUBLES_SINGLE + COMMA_SEP +
                    GameContract.GameEntry.COLUMN_GAME_GAMESETS + INTEGER_TYPE + NOT_NULL + DEFAULT + " " + GAMESETS_5 + COMMA_SEP +
                    GameContract.GameEntry.COLUMN_GAME_GAMESCOREAB + TEXT_TYPE + COMMA_SEP +
                    GameContract.GameEntry.COLUMN_GAME_AGGREGATESCORE + TEXT_TYPE + " )";


    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + GameContract.GameEntry.TABLE_NAME;


    public GameDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);

    }
}
