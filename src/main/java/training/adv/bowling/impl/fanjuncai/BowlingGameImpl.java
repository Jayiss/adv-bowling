package training.adv.bowling.impl.fanjuncai;

import training.adv.bowling.api.*;

import java.util.ArrayList;


public class BowlingGameImpl implements BowlingGame {

    private BowlingTurn firstTurn;
    private BowlingGameEntity bowlingGameEntity = new BowlingGameEntityImpl();
    public LinkedList<BowlingTurn> bowlingTurnLinkedList = new LinkedListImpl();

    public BowlingGameImpl(){
        this.bowlingTurnLinkedList.setNextItem(this.firstTurn);
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
        for(BowlingTurn bowlingTurn:getTurns())
            scores[count] = bowlingTurn.getScore();
        return scores;
    }

    @Override
    public BowlingTurn[] getTurns() {
        return null;

        /*
        if(getFirstTurn()==null)
            return null;
        else{
            ArrayList<BowlingTurn> bowlingTurns = new ArrayList<>();
            LinkedList<BowlingTurn> bowlingTurnLinkedList= new LinkedListImpl();
            BowlingTurn bowlingTurn = bowlingTurnLinkedList.getNextItem();
            while(bowlingTurn !=null){
                bowlingTurns.add(bowlingTurnLinkedList.getNextItem());
                bowlingTurnLinkedList = bowlingTurn.getAsLinkedNode();
                bowlingTurn = bowlingTurnLinkedList.getNextItem();
            }
            return bowlingTurns.toArray(new BowlingTurn[0]);
        }
         */
    }

    @Override
    public BowlingTurn newTurn() {
        return new BowlingTurnImpl();
    }

    @Override
    public Boolean isGameFinished() {

        return null;
    }

    @Override
    public StatusCode addScores(Integer... pins) {

        return null;
    }

    @Override
    public BowlingGameEntity getEntity() {
        return this.bowlingGameEntity;
    }
}
