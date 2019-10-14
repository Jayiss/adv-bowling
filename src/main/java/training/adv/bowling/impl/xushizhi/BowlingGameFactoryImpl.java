package training.adv.bowling.impl.xushizhi;

import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.api.BowlingGameEntity;
import training.adv.bowling.api.BowlingTurn;

import java.util.UUID;

public class BowlingGameFactoryImpl {

    private BowlingGame bowlingGame;

    public BowlingGame getGame() {
        if (bowlingGame != null) {
            return bowlingGame;
        } else {  // Initialize a game  //TODO - Remove All ID Game
            // Generate a random integer based on Java UUID method
            Integer randomInt = UUID.randomUUID().toString().hashCode();
            if (randomInt < 0) {
                randomInt = randomInt * (-1);
            }
            System.out.println(randomInt);

            BowlingGameEntity bowlingGameEntity = new BowlingGameEntityImpl(randomInt, 10, 10);
            BowlingTurn bowlingTurn = new BowlingTurnImpl(10, 10, null);
            bowlingGame = new BowlingGameImpl(bowlingGameEntity, bowlingTurn);
            return bowlingGame;
        }
    }
}
