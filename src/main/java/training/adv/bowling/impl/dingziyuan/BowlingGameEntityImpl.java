package training.adv.bowling.impl.dingziyuan;

import training.adv.bowling.api.BowlingGameEntity;
import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnEntity;

import java.util.UUID;

public class BowlingGameEntityImpl implements BowlingGameEntity {
    private String id = UUID.randomUUID().toString();
    private final Integer MAX_TURN;
    private final Integer MAX_PIN;
    private BowlingTurnEntity[] bowlingTurnEntities = new BowlingTurnEntity[0];

    public BowlingGameEntityImpl(Integer MAX_TURN, Integer MAX_PIN) {
        this.MAX_TURN = MAX_TURN;
        this.MAX_PIN = MAX_PIN;
    }

    @Override
    public Integer getMaxPin() {
        return MAX_PIN;
    }

    @Override
    public void setTurnEntities(BowlingTurnEntity[] turns) {
//        this.bowlingTurnEntities = turns;
//        this.turns = new BowlingTurn[turns.length];
//        for (int i = 0; i < this.turns.length; i++) {
//            this.turns[i]=turns[i].getSecondPin() == null?new BowlingTurnImpl(turns[i].getFirstPin()):new BowlingTurnImpl(turns[i].getFirstPin(), turns[i].getSecondPin());
//            this.turns[i].getEntity().setId(turns[i].getId());
//        }
        this.bowlingTurnEntities = turns;
    }

    @Override
    public BowlingTurnEntity[] getTurnEntities() {
        return this.bowlingTurnEntities;
    }

    @Override
    public Integer getMaxTurn() {
        return MAX_TURN;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}
