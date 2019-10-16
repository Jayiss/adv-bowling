package training.adv.bowling.impl;

import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.api.BowlingGameFactory;
import training.adv.bowling.impl.dingziyuan.BowlingGameImpl;

public class BowlingGameFactoryImpl implements BowlingGameFactory {
    @Override
    public BowlingGame getGame() {
        return new BowlingGameImpl(10,10);
    }
}
