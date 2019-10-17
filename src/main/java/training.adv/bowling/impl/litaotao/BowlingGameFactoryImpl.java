package training.adv.bowling.impl.zhangsan;

import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.api.BowlingGameEntity;
import training.adv.bowling.api.BowlingGameFactory;
import training.adv.bowling.api.BowlingTurn;

import java.util.UUID;

import java.util.concurrent.atomic.AtomicInteger;

public class BowlingGameFactoryImpl implements BowlingGameFactory {

    private AtomicInteger id = new AtomicInteger(0);
    private BowlingGame bowlingGame;
    @Override
    public BowlingGame getGame() {
        if (bowlingGame!=null){
            return bowlingGame;
        }else {
            BowlingGameEntity bowlingGameEntity = new BowlingGameEntityImpl(id.getAndIncrement());
            BowlingTurn bowlingTurn = new BowlingTurnImpl(10,10,null);
            bowlingGame = new BowlingGameImpl(bowlingGameEntity,bowlingTurn);
            return bowlingGame;
        }
    }
}