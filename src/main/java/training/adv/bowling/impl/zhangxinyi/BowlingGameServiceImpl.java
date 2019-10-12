package training.adv.bowling.impl.zhangxinyi;

import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.impl.GameService;

public class BowlingGameServiceImpl extends GameService {

    @Override
    public void play() {
        System.out.println("Now Playing!");
    }

    public BowlingGame getGame() {
        return new BowlingGameImpl();
    }
}
