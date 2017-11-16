package mouseart.com.github.tabletennisscoring.data;

import android.provider.BaseColumns;

/**
 * Created by mouse on 2017/11/16.
 */

public class GameLogContract {

    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private GameLogContract() {
    }

    /* Inner class that defines the table contents */
    public static class GameLogEntry implements BaseColumns {

        /**
         * Name of database table for game
         */
        public static final String TABLE_NAME = "gameLog";

        public static final String COLUMN_GAMELOG_ID = BaseColumns._ID;
        public static final String COLUMN_GAMELOG_GAMEID = "gameID";
        public static final String COLUMN_GAMELOG_EVENTTIME = "eventTime";
        public static final String COLUMN_GAMELOG_EVENTTYPE = "eventType";
        public static final String COLUMN_GAMELOG_GAMESCORE = "gameScore";

    }

}
