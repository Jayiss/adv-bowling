package training.adv.bowling.impl.fanjuncai;

import training.adv.bowling.api.BowlingGameEntity;
import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnEntity;

import java.util.ArrayList;

public class BowlingGameEntityImpl implements BowlingGameEntity {

    private Integer maxPin=10;
    private Integer maxTurn=10;
    private Integer id;
    private BowlingTurnEntity[] bowlingTurnEntities;

    @Override
    public Integer getMaxPin() {
        return this.maxPin;
    }

    @Override
    public void setTurnEntities(BowlingTurnEntity[] turns) {
        /*
        ArrayList<BowlingTurnEntity> bowlingTurnEntityArrayList = new ArrayList<>();
        BowlingTurnImpl bowlingTurn = new BowlingTurnImpl(null);
        for(BowlingTurnEntity TurnEntity:turns){
            BowlingTurnImpl nextBowlingTurn = new BowlingTurnImpl(bowlingTurn);
            BowlingTurnEntityImpl bowlingTurnEntity = new BowlingTurnEntityImpl();
            bowlingTurnEntity = (BowlingTurnEntityImpl) TurnEntity;
            bowlingTurnEntity.setMaxPin(this.maxPin);
            bowlingTurnEntityArrayList.add(bowlingTurnEntity);
            nextBowlingTurn.setEntity(bowlingTurnEntity);
            bowlingTurn.setNextItem(nextBowlingTurn);
            bowlingTurn = (BowlingTurnImpl) bowlingTurn.getNextItem();
        }
        this.bowlingTurnEntities = bowlingTurnEntityArrayList.toArray(new BowlingTurnEntity[0]);

         */
        this.bowlingTurnEntities = turns;
    }

    @Override
    public BowlingTurnEntity[] getTurnEntities() {
        return this.bowlingTurnEntities;
    }

    @Override
    public Integer getMaxTurn() {
        return this.maxTurn;
    }

    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }
}
