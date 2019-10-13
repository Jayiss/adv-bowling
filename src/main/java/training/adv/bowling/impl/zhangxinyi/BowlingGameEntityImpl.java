package training.adv.bowling.impl.zhangxinyi;

import training.adv.bowling.api.BowlingGameEntity;
import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnEntity;

public class BowlingGameEntityImpl implements BowlingGameEntity {
    private Integer id;
    private Integer maxPin = 10;
    private Integer maxTurn = 10;
    private BowlingGameImpl game;
    public static Integer uniqueId = 0;

    public BowlingGameEntityImpl(BowlingGameImpl bowlingGame) {
        game = bowlingGame;
    }

    public BowlingGameImpl getGame() {
        return game;
    }

    @Override
    public Integer getMaxPin() {
        return maxPin;
    }

    @Override
    // Only used in DB
    public void setTurnEntities(BowlingTurnEntity[] turns) {
        int len = turns.length;
        if (len == 0) {
            game.setfirstT(null);
        } else {
            BowlingTurn head = new BowlingTurnImpl(null, maxTurn);
            head.getEntity().setFirstPin(turns[0].getFirstPin());
            head.getEntity().setSecondPin(turns[0].getSecondPin());
            head.getEntity().setId(turns[0].getId());
            game.setfirstT(head);
            for (int i = 1; i < turns.length; i++) {
                BowlingTurn temp = new BowlingTurnImpl((BowlingTurnImpl) head, maxTurn);
                temp.getEntity().setFirstPin(turns[i].getFirstPin());
                temp.getEntity().setSecondPin(turns[i].getSecondPin());
                temp.getEntity().setId(turns[i].getId());
                ((BowlingTurnImpl) head).setNextItem(temp);
                head = ((BowlingTurnImpl) head).getNextItem();
            }
        }
    }

    @Override
    // Only used in DB
    public BowlingTurnEntity[] getTurnEntities() {
        BowlingTurn[] turns = game.getTurns();
        BowlingTurnEntity[] turnEntities = new BowlingTurnEntityImpl[turns.length];
        for (int i = 0; i < turns.length; i++) {
            turnEntities[i] = turns[i].getEntity();
        }
        return turnEntities;
    }

    @Override
    public Integer getMaxTurn() {
        return maxTurn;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }
}
