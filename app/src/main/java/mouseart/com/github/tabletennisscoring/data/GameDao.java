package mouseart.com.github.tabletennisscoring.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by mouse on 2017/11/19.
 */

public class GameDao {

    private static final String TAG = "GameDao";

    private Context context;
    private GameDbHelper gameDbHelper;

    public GameDao(Context context) {
        this.context = context;
        gameDbHelper = new GameDbHelper(context);
    }

    /**
     * 新增一条数据
     */
    public boolean insertNewGame(int mTeamAplayerId, int mTeamBplayerId, int mSinglesDoubles, int mGameSets) {

        SQLiteDatabase db = null;

        try {
            db = gameDbHelper.getWritableDatabase();
            db.beginTransaction();

            // Create a new map of values, where column names are the keys
            ContentValues values = new ContentValues();
            values.put(GameContract.GameEntry.COLUMN_GAME_GAMETITLE,"");
            values.put(GameContract.GameEntry.COLUMN_GAME_GAMESTARTTIME, System.currentTimeMillis());
            values.put(GameContract.GameEntry.COLUMN_GAME_GAMEENDTIME,0);
            values.put(GameContract.GameEntry.COLUMN_GAME_TEAMAPLAYERID1,mTeamAplayerId);
            values.put(GameContract.GameEntry.COLUMN_GAME_TEAMBPLAYERID1,mTeamBplayerId);
            values.put(GameContract.GameEntry.COLUMN_GAME_SINGLESDOUBLES,mSinglesDoubles);
            values.put(GameContract.GameEntry.COLUMN_GAME_SINGLESDOUBLES,mGameSets);

            // Insert the new row, returning the primary key value of the new row
            long newRowId = db.insert(GameContract.GameEntry.TABLE_NAME, null, values);

            db.setTransactionSuccessful();
            return true;
        } catch (SQLiteConstraintException e) {
            Toast.makeText(context, "主键重复", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(TAG, "", e);
        } finally {
            if (db != null) {
                db.endTransaction();
                db.close();
            }
        }
        return false;

    }

}
