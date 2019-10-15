package training.adv.bowling.impl.caokeke;

import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.api.BowlingGameEntity;
import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.StatusCode;
import training.adv.bowling.impl.AbstractGame;

import java.util.ArrayList;
import java.util.List;

public class BowlingGameImpl extends AbstractGame<BowlingTurn, BowlingTurnEntity, BowlingGameEntity> implements BowlingGame,BowlingGameEntity {

    private BowlingTurn firstTurn;
    private Integer MAX_TURNS;
    private Integer MAX_PIN;

    public BowlingGameImpl(Integer maxturn,Integer maxPin){
        this.MAX_TURNS=maxturn;
        this.MAX_PIN=maxPin;
    }

    @Override
    public BowlingTurn getFirstTurn() {
        return firstTurn;
    }

    @Override
    public BowlingTurn[] getTurns() {
        List<BowlingTurn> turns=new ArrayList<>();
        BowlingTurn cur=firstTurn;
        while (cur!=null){
            turns.add(cur);
            cur=cur.getAsLinkedNode().getNextItem();
        }
        return turns.toArray(new BowlingTurn[0]);
    }

    @Override
    public BowlingTurn newTurn() {
        return null;
    }

    @Override
    public Boolean isGameFinished() {
        if (firstTurn==null)return false;
        StatusCode code=firstTurn.addPins(null);
        if (code.getCode().equals(StatusCodeImpl.ADD_FINISHED.getCode()))return true;
        else return false;
    }

    @Override
    public StatusCode addScores(Integer... pins) {
        if (firstTurn==null)firstTurn=new BowlingTurnImpl(null,MAX_PIN);
        return firstTurn.addPins(pins);
    }

    @Override
    public BowlingGameEntity getEntity() {
        return this;
    }

    @Override
    public Integer getMaxPin() {
        return MAX_PIN;
    }

    @Override
    public void setTurnEntities(BowlingTurnEntity[] turns) {
        List<Integer> pins=new ArrayList<>();
        for (BowlingTurnEntity entity :turns) {
            if (entity.getFirstPin()!=null)pins.add(entity.getFirstPin());
            if (entity.getSecondPin()!=null)pins.add(entity.getSecondPin());
        }
        addScores(pins.toArray(new Integer[0]));
    }

    @Override
    public BowlingTurnEntity[] getTurnEntities() {
        List<BowlingTurnEntity> entities=new ArrayList<>();
        BowlingTurn current=firstTurn;
        while (current!=null){
            entities.add(current.getEntity());
            current=current.getAsLinkedNode().getNextItem();
        }
        return entities.toArray(new BowlingTurnEntity[0]);
    }

    @Override
    public Integer getMaxTurn() {
        return MAX_TURNS;
    }

    @Override
    public Integer getId() {
        return null;
    }

    @Override
    public void setId(Integer id) {
    }
}
