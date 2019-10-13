package training.adv.bowling.impl.fanjuncai;

import training.adv.bowling.api.*;

import java.util.ArrayList;


public class BowlingGameImpl implements BowlingGame {

    private BowlingTurnImpl firstTurn = new BowlingTurnImpl(null);
    private BowlingGameEntity bowlingGameEntity = new BowlingGameEntityImpl();
    private Integer tursNum = 1;

    public Integer getTursNum() {
        return tursNum;
    }

    public void setTursNum(Integer tursNum) {
        this.tursNum = tursNum;
    }

    @Override
    public BowlingTurn getFirstTurn() {
        return this.firstTurn;
    }

    @Override
    public int getTotalScore() {
        int totalScore = 0;
        for(int score:getScores())
            totalScore += score;
        return totalScore;
    }

    @Override
    public int[] getScores() {
        int[] scores = new int[bowlingGameEntity.getMaxTurn()];
        int count = 0;
        for(BowlingTurn bowlingTurn:getTurns()){
            scores[count] = bowlingTurn.getScore();
            count++;
            if(count==bowlingGameEntity.getMaxTurn())
                return scores;
        }

        return scores;
    }

    @Override
    public BowlingTurn[] getTurns() {
        ArrayList<BowlingTurn> bowlingTurns = new ArrayList<>();
        BowlingTurnImpl bowlingTurn = (BowlingTurnImpl) this.firstTurn;
        while(bowlingTurn!=null){
            bowlingTurns.add(bowlingTurn);
            bowlingTurn = (BowlingTurnImpl) bowlingTurn.getNextItem();
        }
        return bowlingTurns.toArray(new BowlingTurn[0]);

    }

    @Override
    public BowlingTurn newTurn() {
        return null;
    }

    @Override
    public Boolean isGameFinished() {
        BowlingTurn[] bowlingTurns = getTurns();
        Integer turnLength = bowlingTurns.length;
        if(turnLength<bowlingGameEntity.getMaxTurn())
            return false;
        else {
            if(turnLength==bowlingGameEntity.getMaxTurn()){
                if(!bowlingTurns[turnLength-1].isFinished()&&bowlingTurns[turnLength-1].isSpare())
                    return true;
                else
                    return false;
            }
            else if(turnLength==bowlingGameEntity.getMaxTurn()+1){
                if(bowlingTurns[bowlingGameEntity.getMaxTurn()].getSecondPin()==null
                &&bowlingTurns[bowlingGameEntity.getMaxTurn()-1].isStrike())
                    return false;
                else
                    return true;
            }
        }
        return true;
    }

    @Override
    public StatusCode addScores(Integer... pins) {
        tursNum = 1;
        return firstTurn.addPins(pins);
    }

    @Override
    public BowlingGameEntity getEntity() {
        return this.bowlingGameEntity;
    }
}
