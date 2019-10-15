package training.adv.bowling.impl.caokeke;

import training.adv.bowling.api.BowlingGameEntity;
import training.adv.bowling.api.BowlingTurnEntity;

public class BowlingGameEntityImpl implements BowlingGameEntity {

    private static final int MAX_SCORE=10;
    private static final int MAX_TURNS=10;
    private int id;
    private BowlingTurnEntity[] turns;

    BowlingGameEntityImpl(){id=new ID().getId();}
    BowlingGameEntityImpl(int id){this.id=id;}

    @Override
    public Integer getMaxPin() {
        return MAX_SCORE;
    }

    @Override
    public void setTurnEntities(BowlingTurnEntity[] turns) {
        this.turns=turns;
    }

    @Override
    public BowlingTurnEntity[] getTurnEntities() {
        return turns;
    }

    @Override
    public Integer getMaxTurn() {
        return MAX_TURNS;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id=id;
    }
}
