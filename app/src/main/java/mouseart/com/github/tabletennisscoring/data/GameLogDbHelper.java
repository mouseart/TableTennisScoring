package mouseart.com.github.tabletennisscoring.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mouse on 2017/11/16.
 */
public class GameLogDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "TableTennis.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String PRIMARY_KEY = " PRIMARY KEY";
    private static final String AUTOINCREMENT = " AUTOINCREMENT";
    private static final String NOT_NULL = " NOT NULL";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + GameLogContract.GameLogEntry.TABLE_NAME + " (" +
                    GameLogContract.GameLogEntry.COLUMN_GAMELOG_ID + INTEGER_TYPE + PRIMARY_KEY + AUTOINCREMENT + COMMA_SEP +
                    GameLogContract.GameLogEntry.COLUMN_GAMELOG_GAMEID + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
                    GameLogContract.GameLogEntry.COLUMN_GAMELOG_EVENTTIME + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
                    GameLogContract.GameLogEntry.COLUMN_GAMELOG_EVENTTYPE + INTEGER_TYPE + COMMA_SEP +
                    GameLogContract.GameLogEntry.COLUMN_GAMELOG_GAMESCORE + TEXT_TYPE + " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + GameLogContract.GameLogEntry.TABLE_NAME;


    public GameLogDbHelper(Context context) {
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
