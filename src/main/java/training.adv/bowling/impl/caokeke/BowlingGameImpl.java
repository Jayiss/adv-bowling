package training.adv.bowling.impl.caokeke;

import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.api.BowlingGameEntity;
import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.StatusCode;

import java.util.ArrayList;
import java.util.List;

public class BowlingGameImpl implements BowlingGame {

    //private LinkedList<BowlingTurn> firstNode;
    private BowlingTurn firstTurn;
    private BowlingGameEntity bge;

    public BowlingGameImpl(){
        firstTurn=new BowlingTurnImpl();
    }


    @Override
    public BowlingTurn getFirstTurn() {
        return firstTurn;
    }

    @Override
    public int getTotalScore() {
        int res=0;
        int []scores=getScores();
        if(scores==null)return 0;
        for(int i=1;i<=scores.length;i++)
            res+=scores[i];
        return res;
    }

    private int[] calcScores(BowlingTurn[] allTurns) {
        if(allTurns.length==0)
            return null;


        int [] res;
        if(allTurns.length>=bge.getMaxTurn())
            res=new int[bge.getMaxTurn()];
        else
            res=new int[allTurns.length];

        int i;
        for(i=0;i<res.length;i++){
            if(allTurns[i].isValid()){
                res[i]=allTurns[i].getFirstPin();
                if(allTurns[i].getSecondPin()!=null)res[i]+=allTurns[i].getSecondPin();
                if(i-1>=0 && allTurns[i-1].isSpare())
                    res[i-1]+=allTurns[i].getFirstPin();

                if(i-1>=0 && allTurns[i].getSecondPin()!=null && allTurns[i].getSecondPin()>0 && allTurns[i-1].isStrike())
                    res[i-1]+=allTurns[i].getSecondPin();

                if(i-2>=0 && allTurns[i-1].getSecondPin()!=null && allTurns[i-1].getSecondPin()==0 && allTurns[i-2].isStrike())
                    res[i-2]+=allTurns[i].getFirstPin();
            }
            else{
                System.out.println("invalid");
                break;
            }
        }

        if(i<allTurns.length){
            if(i-1>=0 && allTurns[i-1].isSpare())
                res[i-1]+=allTurns[i].getFirstPin();
            if(i-1>=0 && allTurns[i].getSecondPin()!=null && allTurns[i].getSecondPin()>0 && allTurns[i-1].isStrike())
                res[i-1]+=allTurns[i].getSecondPin();
            if(i-2>=0 && allTurns[i-1].getSecondPin()==0 && allTurns[i-2].isStrike())
                res[i-2]+=allTurns[i].getFirstPin();
            if(i+1<allTurns.length && allTurns[i-1].getSecondPin()==0 && allTurns[i-1].isStrike())
                res[i-1]+=allTurns[i+1].getFirstPin();
        }

        return res;
    }

    @Override
    public int[] getScores() {
        BowlingTurn []turns=getTurns();
        if(turns==null)
            return null;
        return calcScores(turns);
    }

    @Override
    public BowlingTurn[] getTurns() {
        if(firstTurn.getEntity().getFirstPin()==null)return null;
        List<BowlingTurn> tmp=new ArrayList<BowlingTurn>();
        tmp.add(firstTurn);

        BowlingTurn next=firstTurn.getAsLinkedNode().getNextItem();

        while(next.getAsLinkedNode()!=null){
            tmp.add(next);
            next=next.getAsLinkedNode().getNextItem();
        }
        return tmp.toArray(new BowlingTurn[tmp.size()]);
    }

    @Override
    public BowlingTurn newTurn() {
        return null;
    }

    @Override
    public Boolean isGameFinished() {
        BowlingTurnEntity[] allTurns=bge.getTurnEntities();
        if(bge.getTurnEntities().length<bge.getMaxTurn() || allTurns[allTurns.length-1].getSecondPin()==null)
            return false;

        Integer lastPin1=allTurns[bge.getMaxTurn()-1].getFirstPin();
        Integer lastPin2=allTurns[bge.getMaxTurn()-1].getSecondPin();


        return (lastPin1==bge.getMaxPin() && allTurns.length==bge.getMaxTurn()+2) ||
                (lastPin1<bge.getMaxPin() && lastPin1+lastPin2==bge.getMaxPin() && allTurns.length==bge.getMaxTurn()+1) ||
                (lastPin1+lastPin2<10 && allTurns.length==bge.getMaxTurn());
    }

    @Override
    public StatusCode addScores(Integer... pins) {
        return firstTurn.addPins(pins);
    }

    @Override
    public BowlingGameEntity getEntity() {
        return bge;
    }
}
