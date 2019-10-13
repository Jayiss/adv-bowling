package training.adv.bowling.impl.why;

import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.api.BowlingGameEntity;
import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.StatusCode;
import training.adv.bowling.impl.AbstractGame;

import java.util.ArrayList;
import java.util.List;

public class BowlingGameImpl extends AbstractGame<BowlingTurn, BowlingTurnEntity, BowlingGameEntity> implements BowlingGame,BowlingGameEntity {

    private BowlingTurn first=null;

    private Integer maxTurn;
    private Integer maxPin;
    private Integer gameId;

    public BowlingGameImpl(Integer maxturn,Integer maxPin,Integer gameId){
        this.maxTurn=maxturn;
        this.maxPin=maxPin;
        this.gameId=gameId;
    }

    @Override
    public BowlingTurn getFirstTurn() {
        return first;
    }

    @Override
    public BowlingTurn[] getTurns() {
        List<BowlingTurn> turns=new ArrayList<>();
        BowlingTurn cur=first;
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
        if (first==null)return false;
        StatusCode code=first.addPins(null);
        if (code.getCode().equals(StatusCodeImpl.FINISHED.getCode()))return true;
        else return false;
    }

    @Override
    public StatusCode addScores(Integer... pins) {
        if (first==null)first=new BowlingTurnImpl(null,maxPin);
        return first.addPins(pins);
    }

    @Override
    public BowlingGameEntity getEntity() {
        return this;
    }

    @Override
    public Integer getMaxPin() {
        return maxPin;
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
        BowlingTurn current=first;
        while (current!=null){
            entities.add(current.getEntity());
            current=current.getAsLinkedNode().getNextItem();
        }
        return entities.toArray(new BowlingTurnEntity[0]);
    }

    @Override
    public Integer getMaxTurn() {
        return maxTurn;
    }

    @Override
    public Integer getId() {
        return gameId;
    }

    @Override
    public void setId(Integer id) {
        this.gameId=id;
    }
}
