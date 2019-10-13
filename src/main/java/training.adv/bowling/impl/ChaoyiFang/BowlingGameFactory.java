package training.adv.bowling.impl.ChaoyiFang;

import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.api.BowlingGameEntity;
import training.adv.bowling.api.BowlingTurn;

import java.util.UUID;

public class BowlingGameFactory {
    private BowlingGame bowlingGame;
    public BowlingGame getGame() {
        if (bowlingGame!=null){
            return bowlingGame;
        }else {
            Integer randomInt = UUID.randomUUID().toString().hashCode();
            if(randomInt<0){
                randomInt = randomInt*(-1);
            }
            BowlingGameEntity bowlingGameEntity = new BowlingGameEntityImpl(randomInt,10);
            BowlingTurn bowlingTurn = new BowlingTurnImpl(10,10,null);
            bowlingGame =  new BowlingGameImpl(bowlingGameEntity,bowlingTurn);
            return bowlingGame;
        }
    }
}
