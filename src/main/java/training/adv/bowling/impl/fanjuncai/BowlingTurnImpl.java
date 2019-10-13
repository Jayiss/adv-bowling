package training.adv.bowling.impl.fanjuncai;

import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.LinkedList;
import training.adv.bowling.api.StatusCode;

import java.util.ArrayList;

public class BowlingTurnImpl implements BowlingTurn,LinkedList {

    private BowlingTurnEntityImpl bowlingTurnEntity = new BowlingTurnEntityImpl();
    private BowlingTurnImpl previousItem;
    private BowlingTurnImpl nextItem;
    private Integer count=1;



    public BowlingTurnImpl(BowlingTurn bowlingTurn){
        previousItem = (BowlingTurnImpl) bowlingTurn;
    }

    public void setEntity(BowlingTurnEntity bowlingTurnEntity) {
        this.bowlingTurnEntity = (BowlingTurnEntityImpl) bowlingTurnEntity;
    }

    @Override
    public Boolean isStrike() {
        if(bowlingTurnEntity.getFirstPin()!=null&&bowlingTurnEntity.getFirstPin()==bowlingTurnEntity.getMaxPin())
            return true;
        else
            return false;
    }

    @Override
    public Boolean isSpare() {
        if(bowlingTurnEntity.getFirstPin()!=null
                &&bowlingTurnEntity.getSecondPin()!=null
                &&(bowlingTurnEntity.getSecondPin()+bowlingTurnEntity.getFirstPin()==bowlingTurnEntity.getMaxPin()))
            return true;
        else
            return false;
    }

    @Override
    public Boolean isMiss() {
        if(bowlingTurnEntity.getFirstPin()!=null
                &&bowlingTurnEntity.getSecondPin()!=null
                &&(bowlingTurnEntity.getSecondPin()+bowlingTurnEntity.getFirstPin()<bowlingTurnEntity.getMaxPin()))
            return true;
        else
            return false;
    }

    @Override
    public Integer getFirstPin() {
        return this.bowlingTurnEntity.getFirstPin();
    }

    @Override
    public Integer getSecondPin() {
        return this.bowlingTurnEntity.getSecondPin();
    }

    @Override
    public StatusCode addPins(Integer... pins) {

        if(this.count>bowlingTurnEntity.getMaxTurn()+2){
            if(!this.previousItem.isStrike())
                return StatusCodeImpl.OUTOFRANGE;
            else if(pins!=null&&pins.length!=0)
                return StatusCodeImpl.OUTOFRANGE;
        }
        else if(this.count==bowlingTurnEntity.getMaxTurn()+2){
            if(this.getSecondPin()!=null)
                return StatusCodeImpl.OUTOFRANGE;
            else{
                if(this.getFirstPin()!=null&&(!this.previousItem.isStrike()))
                    return StatusCodeImpl.OUTOFRANGE;
            }
        }
        else if(this.count==bowlingTurnEntity.getMaxTurn()+1){
            if(pins!=null&&pins.length!=0){
               if(this.previousItem.isMiss())
                   return StatusCodeImpl.OUTOFRANGE;
               else if(!this.previousItem.isStrike()&&this.bowlingTurnEntity.getFirstPin()!=null)
                   return StatusCodeImpl.OUTOFRANGE;
            }
            else {
                if(this.previousItem.isMiss()&&this.bowlingTurnEntity.getFirstPin()!=null)
                    return StatusCodeImpl.OUTOFRANGE;
                else if(this.previousItem.isSpare()&&this.bowlingTurnEntity.getSecondPin()!=null)
                    return StatusCodeImpl.OUTOFRANGE;
            }

        }

        if (pins == null || pins.length == 0){
            return StatusCodeImpl.SUCCESS;
        }

        Integer isModified = 0;
        StatusCode statusCode = StatusCodeImpl.SUCCESS;
        ArrayList<Integer> pinsArray = new ArrayList<>();

        for (Integer pin : pins)
            pinsArray.add(pin);

        Integer pin = pins[0];

        if (pin < 0)
            return StatusCodeImpl.NEGATIVE;
        else if (pin > bowlingTurnEntity.getMaxPin())
            return StatusCodeImpl.BIG;
        else {
            if (this.isFinished()) {
                if (this.nextItem == null||this.bowlingTurnEntity.getFirstPin()==null){
                    this.setNextItem(new BowlingTurnImpl(this));
                }
                this.nextItem.count = this.count+1;
                statusCode = this.nextItem.addPins(pins);
            } else {
                if (this.getSecondPin() == null) {
                    if (this.getFirstPin() == null) {
                        this.bowlingTurnEntity.setFirstPin(pin);
                        pinsArray.remove(0);
                        pins = pinsArray.toArray(new Integer[0]);
                        isModified++;
                        statusCode = this.addPins(pins);
                    } else {
                        if (this.getFirstPin() + pin > 10)
                            return StatusCodeImpl.SUM;
                        else {
                            this.bowlingTurnEntity.setSecondPin(pin);
                            pinsArray.remove(0);
                            pins = pinsArray.toArray(new Integer[0]);
                            isModified++;
                            if (this.nextItem == null)
                                this.setNextItem(new BowlingTurnImpl(this));
                            this.nextItem.count = this.count+1;
                            statusCode = this.nextItem.addPins(pins);
                        }
                    }
                } else {
                    if (this.nextItem == null)
                        this.setNextItem(new BowlingTurnImpl(this));
                    this.nextItem.count = this.count+1;
                    statusCode = this.nextItem.addPins(pins);
                }
            }
        }
        if (statusCode.getCode().equals("failure")) {
            if (isModified == 2) {
                this.bowlingTurnEntity.setFirstPin(null);
                this.bowlingTurnEntity.setSecondPin(null);
            } else if (isModified == 1 && this.getSecondPin()==null){
                this.bowlingTurnEntity.setFirstPin(null);
            }
            else if(isModified==1&&this.getSecondPin()!=null)
                this.bowlingTurnEntity.setSecondPin(null);
        }
        return statusCode;
    }

    @Override
    public LinkedList<BowlingTurn> getAsLinkedNode(){
        return this;
    }

    @Override
    public BowlingTurnEntity getEntity() {
        return this.bowlingTurnEntity;
    }

    @Override
    public Boolean isFinished() {
        if(bowlingTurnEntity.getFirstPin()==null)
            return false;
        else{
            if(bowlingTurnEntity.getSecondPin()==null&&bowlingTurnEntity.getFirstPin()!=bowlingTurnEntity.getMaxPin())
                return false;
            else
                return true;
        }
    }

    @Override
    public Integer getScore() {
        Integer score = 0;
        if(!this.isFinished()){
            if(this.bowlingTurnEntity.getFirstPin()==null)
                return 0;
            else if(this.bowlingTurnEntity.getSecondPin()==null)
                return bowlingTurnEntity.getFirstPin();
        }
        else{
            if(this.isStrike()){
                if(this.nextItem!=null){
                    if(!this.nextItem.isFinished()){
                        if(this.nextItem.bowlingTurnEntity.getFirstPin()==null)
                            return bowlingTurnEntity.getMaxPin();
                        else
                            return bowlingTurnEntity.getMaxPin() + this.nextItem.bowlingTurnEntity.getFirstPin();
                    }
                    else {
                        if(this.nextItem.isStrike()){
                            if(this.nextItem.nextItem!=null&&this.nextItem.nextItem.bowlingTurnEntity.getFirstPin()!=null)
                                return 2*bowlingTurnEntity.getMaxPin()+this.nextItem.nextItem.bowlingTurnEntity.getFirstPin();
                            else
                                return 2*bowlingTurnEntity.getMaxPin();
                        }
                        else
                            return  bowlingTurnEntity.getMaxPin()
                                    +this.nextItem.bowlingTurnEntity.getFirstPin()
                                    +this.nextItem.bowlingTurnEntity.getSecondPin();
                    }
                }
                else
                    return bowlingTurnEntity.getMaxPin();
            }
            else if(this.isSpare()){
                if(this.nextItem!=null&&this.nextItem.bowlingTurnEntity.getFirstPin()!=null)
                    return bowlingTurnEntity.getMaxPin()+this.nextItem.bowlingTurnEntity.getFirstPin();
                else
                    return bowlingTurnEntity.getMaxPin();
            }
            else
                return bowlingTurnEntity.getFirstPin()+bowlingTurnEntity.getSecondPin();

        }
        return score;
    }

    @Override
    public Boolean isValid() {
        return null;
    }

    @Override
    public BowlingTurnImpl getNextItem() {
        return nextItem;
    }

    @Override
    public void setNextItem(Object item) {
        this.nextItem = (BowlingTurnImpl) item;
    }

    @Override
    public Object getPreviousItem() {
        return previousItem;
    }
}
