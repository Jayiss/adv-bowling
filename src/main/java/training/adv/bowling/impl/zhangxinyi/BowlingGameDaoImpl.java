package training.adv.bowling.impl.zhangxinyi;

import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.api.BowlingGameEntity;
import training.adv.bowling.impl.AbstractDao;

public class BowlingGameDaoImpl extends AbstractDao<BowlingGameEntity, BowlingGame, Integer> {
    @Override
    protected void doSave(BowlingGameEntity entity) {
        //TODO
    }

    @Override
    protected BowlingGameEntity doLoad(Integer id) {
        //TODO
        return null;
    }

    @Override
    protected BowlingGame doBuildDomain(BowlingGameEntity entity) {
        //TODO
        return null;
    }

    @Override
    public boolean remove(Integer key) {
        //TODO
        return false;
    }
}
