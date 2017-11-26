package mouseart.com.github.tabletennisscoring.data;

import android.provider.BaseColumns;

/**
 * Created by mouse on 2017/11/14.
 */

public final class GameContract {

    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private GameContract() {
    }

    /* Inner class that defines the table contents */
    public static class GameEntry implements BaseColumns {

        /**
         * Name of database table for game
         */
        public static final String TABLE_GAME_NAME = "game";

        public static final String COLUMN_GAME_ID="_Id";
        public static final String COLUMN_GAME_GAMETITLE="gameTitle";
        public static final String COLUMN_GAME_GAMESTARTTIME="gameStartTime";
        public static final String COLUMN_GAME_GAMEENDTIME="gameEndTime";
        public static final String COLUMN_GAME_TEAMAPLAYERID1="teamAPlayerId1";
        public static final String COLUMN_GAME_TEAMAPLAYERID2="teamAPlayerId2";
        public static final String COLUMN_GAME_TEAMBPLAYERID1="teamBPlayerId1";
        public static final String COLUMN_GAME_TEAMBPLAYERID2="teamBPlayerId2";
        public static final String COLUMN_GAME_SINGLESDOUBLES="singlesDoubles";
        public static final String COLUMN_GAME_GAMESETS="gameSets";
        public static final String COLUMN_GAME_GAMESCORE1A="gameScore1A";
        public static final String COLUMN_GAME_GAMESCORE1B="gameScore1B";
        public static final String COLUMN_GAME_GAMESCORE2A="gameScore2A";
        public static final String COLUMN_GAME_GAMESCORE2B="gameScore2B";
        public static final String COLUMN_GAME_GAMESCORE3A="gameScore3A";
        public static final String COLUMN_GAME_GAMESCORE3B="gameScore3B";
        public static final String COLUMN_GAME_GAMESCORE4A="gameScore4A";
        public static final String COLUMN_GAME_GAMESCORE4B="gameScore4B";
        public static final String COLUMN_GAME_GAMESCORE5A="gameScore5A";
        public static final String COLUMN_GAME_GAMESCORE5B="gameScore5B";
        public static final String COLUMN_GAME_GAMESCORE6A="gameScore6A";
        public static final String COLUMN_GAME_GAMESCORE6B="gameScore6B";
        public static final String COLUMN_GAME_GAMESCORE7A="gameScore7A";
        public static final String COLUMN_GAME_GAMESCORE7B="gameScore7B";
        public static final String COLUMN_GAME_AGGREGATESCOREA="aggregateScoreA";
        public static final String COLUMN_GAME_AGGREGATESCOREB="aggregateScoreB";

        /**
         * Possible values for the gender of the game.
         */
        public static final int SINGLESDOUBLES_SINGLE = 1;
        public static final int SINGLESDOUBLES_DOUBLES = 2;

        public static final int GAMESETS_3 = 3;
        public static final int GAMESETS_5 = 5;
        public static final int GAMESETS_7 = 7;


    }
}
