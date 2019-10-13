package training.adv.bowling.impl.zhuyurui;

import training.adv.bowling.api.*;

import java.util.Arrays;

public class BowlingTurnImpl implements BowlingTurn {

    private BowlingTurnEntity bowlingTurnEntity = new BowlingTurnEntityImpl();
    private LinkedList<BowlingTurn> pointer = new LinkedListImpl<BowlingTurn>(null);
    //private StatusCode status;
    private int maxPin=0;

    public BowlingTurnImpl(int maxPin) {
        this.maxPin = maxPin;

    }

    public BowlingTurnImpl(LinkedList<BowlingTurn> btl, int maxPin) {
        this(maxPin);
        pointer = btl;
    }

    public BowlingTurnImpl(Integer firstPin, Integer secondPin) {
        this.bowlingTurnEntity.setFirstPin(firstPin);
        this.bowlingTurnEntity.setSecondPin(secondPin);

    }

    public BowlingTurnImpl(Integer firstPin, Integer secondPin, Integer maxPin) {
        this.bowlingTurnEntity.setFirstPin(firstPin);
        this.bowlingTurnEntity.setSecondPin(secondPin);
        this.maxPin = maxPin;

    }

    public BowlingTurnImpl(Integer firstPin) {
        this(firstPin, null);
    }

    public BowlingTurnImpl(BowlingTurnEntity entity,int maxPin) {
        this(entity.getFirstPin(), entity.getSecondPin());
        this.bowlingTurnEntity.setId(entity.getId());
        this.maxPin=maxPin;
    }

    public BowlingTurnImpl(BowlingTurnEntity entity,LinkedList<BowlingTurn> btl,int maxPin) {
        this(entity,maxPin);
        this.pointer=btl;
    }


    @Override
    public Boolean isStrike() {
        if (getSecondPin() != null) {
            if (getFirstPin() == maxPin) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Boolean isSpare() {
        return (getFirstPin() + getSecondPin() == maxPin);

    }

    @Override
    public Boolean isMiss() {

        return (getFirstPin() + getSecondPin() < maxPin);
    }

    @Override
    public Integer getFirstPin() {

        return bowlingTurnEntity.getFirstPin();
    }

    @Override
    public Integer getSecondPin() {
        return bowlingTurnEntity.getSecondPin();
    }


    @Override
    public StatusCode addPins(Integer... pins) {

        if (pins.length != 0) {
            int pos=0;
            int status=0;
            if (getFirstPin() == null) {

                bowlingTurnEntity.setFirstPin(pins[0]);
                pos = 1;
                if (pins.length > 1) {
                    bowlingTurnEntity.setSecondPin(pins[1]);
                    pos = 2;
                }
                status=2;
            } else if(getSecondPin()==null){
                bowlingTurnEntity.setSecondPin(pins[0]);
                pos = 1;
                status=1;
            }
            if (!isValid()) {
                return StatusCodeImpl.FAILED;
            }

            LinkedList<BowlingTurn> bowlingTurnLinkedList = new LinkedListImpl<BowlingTurn>(this);
            if(pins.length-pos==0){
                return StatusCodeImpl.SUCCEED;
            }
            pointer.setNextItem(new BowlingTurnImpl(bowlingTurnLinkedList, maxPin));

            StatusCode statusCode = pointer.getNextItem().addPins(Arrays.copyOfRange(pins, pos, pins.length));
            if (statusCode == StatusCodeImpl.FAILED) {
                if (status == 0) {
                    pointer.setNextItem(null);

                }
                if (status == 1) {
                    bowlingTurnEntity.setSecondPin(null);
                }
                return StatusCodeImpl.FAILED;
            }

        }
        return StatusCodeImpl.SUCCEED;
    }


    @Override
    public LinkedList<BowlingTurn> getAsLinkedNode() {
        return pointer;
    }


    @Override
    public Boolean isFinished() {
        return (getSecondPin() != null && getFirstPin() != null);

    }

    @Override
    public Integer getScore() {
        int nextOne = 0;
        int nextTwo = 0;
        int score;
        if (getFirstPin() == null) {
            return 0;
        }
        if (isStrike()) {
            if (pointer.getNextItem() != null) {
                BowlingTurn nextTurn = pointer.getNextItem();
                if (nextTurn.isStrike()) {
                    nextOne = maxPin;
                    if (nextTurn.getAsLinkedNode().getNextItem() != null) {
                        nextTwo = nextTurn.getAsLinkedNode().getNextItem().getFirstPin();
                    }
                } else {
                    nextOne = nextTurn.getFirstPin();
                    if (nextTurn.getSecondPin() != null) {
                        nextTwo = nextTurn.getSecondPin();
                    }
                }
            }
            score = maxPin + nextOne + nextTwo;
        } else {
            if (isFinished()) {
                if (isSpare()) {
                    //find next one
                    if (pointer.getNextItem() != null) {
                        nextOne = pointer.getNextItem().getFirstPin();
                    }
                    score = maxPin + nextOne;
                } else {
                    if (getSecondPin() != null) {
                        score = getFirstPin() + getSecondPin();
                    } else {
                        score = getFirstPin();
                    }

                }
            } else {
                score = getFirstPin();
            }
        }
        return score;
    }

    @Override
    public Boolean isValid() {

        if (getSecondPin() != null) {
            if (getFirstPin() + getSecondPin() > maxPin) {
                return false;
            }
            if (getFirstPin() < 0 | getSecondPin() < 0) {
                return false;
            }
        } else {
            if (getFirstPin() > maxPin) {
                return false;
            }
            if (getFirstPin() < 0) {
                return false;
            }
        }
        return true;
    }


    @Override
    public BowlingTurnEntity getEntity() {
        return bowlingTurnEntity;
    }
}
