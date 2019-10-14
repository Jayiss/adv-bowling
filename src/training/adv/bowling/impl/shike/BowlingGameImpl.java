package training.adv.bowling.impl.shike;

import java.util.ArrayList;
import java.util.function.IntFunction;



import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.api.BowlingGameEntity;
import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.LinkedList;
import training.adv.bowling.api.StatusCode;
import training.adv.bowling.impl.AbstractGame;

public class BowlingGameImpl extends AbstractGame<BowlingTurn, BowlingTurnEntity, BowlingGameEntity> implements BowlingGame{

	Integer maxPin=10;
	Integer maxTurn=10;
	
	BowlingGameEntity gameEntity;
	BowlingTurnEntity[] bowlingTurnEntities;
	
	BowlingTurn firstTurn=null;
	
	public BowlingGameImpl() {
		gameEntity = new BowlingGameEntityImpl(10,10);
		bowlingTurnEntities=new BowlingTurnEntity[0];
		firstTurn = new BowlingTurnImpl(new BowlingTurnEntityImpl());
	}
	
	public BowlingGameImpl(BowlingGameEntity gameEntity) {
		this.bowlingTurnEntities=gameEntity.getTurnEntities();
		this.gameEntity=gameEntity;
//		if (gameEntity.getTurnEntities().length==0) {
//			firstTurn = new BowlingTurnImpl();
//		}else {
//			firstTurn = new BowlingTurnImpl(maxPin, maxTurn, gameEntity.getTurnEntities()[0]);
//		}
		firstTurn = new BowlingTurnImpl(new BowlingTurnEntityImpl());
		
	}
	
	
	@Override
	public BowlingTurn[] getTurns() {
		ArrayList<BowlingTurn> list = new ArrayList<BowlingTurn>();
		if (firstTurn.getAsLinkedNode().getNextItem()==null) {
			return new BowlingTurn[0];
		}else {
			BowlingTurn nowTurn=firstTurn;
			while (nowTurn.getAsLinkedNode().getNextItem()!=null) {
				nowTurn = nowTurn.getAsLinkedNode().getNextItem();
				list.add(nowTurn);
			}
		}
//		BowlingTurn[] arr = list.toArray(new BowlingTurn[list.size()]);
//		for (int i = 0; i < arr.length; i++) {
//			System.out.println(arr[i].getFirstPin()+","+arr[i].getSecondPin());
//		}
		return list.toArray(new BowlingTurn[list.size()]);
	}



	@Override
	public Boolean isGameFinished() { // turn的长度是否超过了
		BowlingTurn[] allTurns = getTurns();
		
		if (allTurns.length>maxTurn+2) {
		return true;
		}
		if (allTurns.length==maxTurn+2 ) {
			if (!allTurns[maxTurn-1].isStrike()) {
				return true;
			}else {
				if (!allTurns[maxTurn].isStrike()) {
					return true;
				}
				if (allTurns[maxTurn+1].isSpare()) {
					return true;
				}
			}
		}
		if (allTurns.length==maxTurn+1 ) {
			if (allTurns[maxTurn-1].isMiss()) {
				return true;
			}else {
				if (allTurns[maxTurn-1].isSpare() && allTurns[maxTurn].isFinished()) {
						return true;
					}
				}
			}
		return false;
	
	}

	@Override
	public StatusCode addScores(Integer... pins) { 
		BowlingTurn nowTurn= getFirstTurn();
		BowlingTurn lastTurn=nowTurn;
		while (nowTurn.getAsLinkedNode().getNextItem()!=null) {
			nowTurn=nowTurn.getAsLinkedNode().getNextItem();
			lastTurn = nowTurn;
		}
		Integer fpinCopy = lastTurn.getFirstPin();
		Integer spinCopy = lastTurn.getSecondPin();

		if (lastTurn.addPins(pins)==StatusCodeImpl.FAIL) {
			lastTurn.getEntity().setFirstPin(fpinCopy);
			lastTurn.getEntity().setSecondPin(spinCopy);
			lastTurn.getAsLinkedNode().setNextItem(null);
			return StatusCodeImpl.FAIL;
		}else {
			if (!isGameFinished()) {
				return StatusCodeImpl.SUCCESS;
			}else {				
				lastTurn.getEntity().setFirstPin(fpinCopy);
				lastTurn.getEntity().setSecondPin(spinCopy);
				lastTurn.getAsLinkedNode().setNextItem(null);
				return StatusCodeImpl.FAIL;
			}
		}
	}

	@Override
	public BowlingGameEntity getEntity() {return this.gameEntity;}

	@Override
	public BowlingTurn getFirstTurn() {
		return this.firstTurn;
	}
	
	@Override
	public BowlingTurn newTurn() { 
//		BowlingTurnEntity bte = new BowlingTurnEntityImpl();
//		Integer id = new GetNumber().getTurnNum();
//		bte.setId(new TurnKeyImpl(id, gameEntity.getId()));
//		BowlingTurn bt = new BowlingTurnImpl();
//		return bt;
		BowlingTurn newTurn = new BowlingTurnImpl(maxPin, maxTurn,new BowlingTurnEntityImpl());
		return newTurn;
	}

	@Override
	public int[] getScores() {
		ArrayList<Integer> list = new ArrayList<Integer>();
		LinkedList<BowlingTurn>  nowNode= firstTurn.getAsLinkedNode();
		int i=0;
		while(nowNode.getNextItem()!=null) {
			list.add(nowNode.getNextItem().getScore());
			i++;
			if (i==maxPin) {
				break;
			}
			nowNode=nowNode.getNextItem().getAsLinkedNode();
		}
		Integer[] scores = list.toArray(new Integer[list.size()]);
		int[] scores2 = new int[scores.length];
		for (int j = 0; j < scores.length; j++) {
			scores2[j] = scores[j];
		}
		return scores2;
	}
}
