package training.adv.bowling.impl.caoyu;

class UidUtil {
    private static Integer gameId = 10000, turnId = 1000;

    synchronized static Integer getNewGameId() {
        return gameId++;
    }

    synchronized static Integer getNewTurnId() {
        return turnId++;
    }
}
