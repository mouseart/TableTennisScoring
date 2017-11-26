package mouseart.com.github.tabletennisscoring.data;

/**
 * Created by mouse on 2017/11/19.
 */

public class Game {
    public static int game_id;
    public String gameTitle;
    public int gameStartTime;
    public int gameEndTime;
    public int teamAPlayerId1;
    public int teamAPlayerId2;
    public int teamBPlayerId1;
    public int teamBPlayerId2;
    public int singlesDoubles;
    public int gameSets;
    public int gameScore1A;
    public int gameScore1B;
    public int gameScore2A;
    public int gameScore2B;
    public int gameScore3A;
    public int gameScore3B;
    public int gameScore4A;
    public int gameScore4B;
    public int gameScore5A;
    public int gameScore5B;
    public int gameScore6A;
    public int gameScore6B;
    public int gameScore7A;
    public int gameScore7B;
    public int aggregateScoreA;
    public int aggregateScoreB;


    public Game() {
    }

    public Game(int game_id,
                String gameTitle,
                int gameStartTime,
                int gameEndTime,
                int teamAPlayerId1,
                int teamAPlayerId2,
                int teamBPlayerId1,
                int teamBPlayerId2,
                int singlesDoubles,
                int gameSets,
                int gameScore1A,
                int gameScore1B,
                int gameScore2A,
                int gameScore2B,
                int gameScore3A,
                int gameScore3B,
                int gameScore4A,
                int gameScore4B,
                int gameScore5A,
                int gameScore5B,
                int gameScore6A,
                int gameScore6B,
                int gameScore7A,
                int gameScore7B,
                int aggregateScoreA,
                int aggregateScoreB) {
        this.game_id = game_id;
        this.gameTitle = gameTitle;
        this.gameStartTime = gameStartTime;
        this.gameEndTime = gameEndTime;
        this.teamAPlayerId1 = teamAPlayerId1;
        this.teamAPlayerId2 = teamAPlayerId2;
        this.teamBPlayerId1 = teamBPlayerId1;
        this.teamBPlayerId2 = teamBPlayerId2;
        this.singlesDoubles = singlesDoubles;
        this.gameSets = gameSets;
        this.gameScore1A = gameScore1A;
        this.gameScore1B = gameScore1B;
        this.gameScore2A = gameScore2A;
        this.gameScore2B = gameScore2B;
        this.gameScore3A = gameScore3A;
        this.gameScore3B = gameScore3B;
        this.gameScore4A = gameScore4A;
        this.gameScore4B = gameScore4B;
        this.gameScore5A = gameScore5A;
        this.gameScore5B = gameScore5B;
        this.gameScore6A = gameScore6A;
        this.gameScore6B = gameScore6B;
        this.gameScore7A = gameScore7A;
        this.gameScore7B = gameScore7B;
        this.aggregateScoreA = aggregateScoreA;
        this.aggregateScoreB = aggregateScoreB;
    }
}
