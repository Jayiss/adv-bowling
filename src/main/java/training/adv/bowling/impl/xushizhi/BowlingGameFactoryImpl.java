package training.adv.bowling.impl.xushizhi;

import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.api.BowlingGameEntity;
import training.adv.bowling.api.BowlingTurn;

public class BowlingGameFactoryImpl {

    private BowlingGame bowlingGame;

    public BowlingGame getGame() {
        if (bowlingGame != null) {
            return bowlingGame;
        } else {  // Initialize a game
            BowlingGameEntity bowlingGameEntity = new BowlingGameEntityImpl(null, 10, 10);
            BowlingTurn bowlingTurn = new BowlingTurnImpl(10, 10, null);
            bowlingGame = new BowlingGameImpl(bowlingGameEntity, bowlingTurn);
            return bowlingGame;
        }
    }
}
