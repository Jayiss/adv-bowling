package wangbingchao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.api.BowlingGameEntity;
import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.StatusCode;

public class BowlingGameImpl implements BowlingGame {

	private BowlingGameEntity entity = new BowlingGameEntityImpl();
	private BowlingTurn firstTurn;
	public BowlingGameImpl(BowlingGameEntity entity) {
		this.entity = entity;
	}
	public BowlingGameImpl() {
		
	}
	@Override
	public int getTotalScore() {
		// TODO Auto-generated method stub
		int sum = 0;
		
		for(int i = 0;i < this.getScores().length;i++) {
			if(i == this.getEntity().getMaxTurn())
				break;
			sum += this.getScores()[i];
		}
		return sum;
	}

	@Override
	public int[] getScores() {
		// TODO Auto-generated method stub
		BowlingTurn [] turns = this.getTurns();
		int [] scores = new int[turns.length];
		for(int i = 0;i < scores.length;i++) {
			scores[i] = turns[i].getScore();
		}
		return scores;
	}

	@Override
	public BowlingTurn[] getTurns() {
		// TODO Auto-generated method stub
		
		ArrayList<BowlingTurn> list = new ArrayList<BowlingTurn>();
		BowlingTurn turn = this.getFirstTurn();
		
		while(turn != null) {
			list.add(turn);
			turn = turn.getAsLinkedNode().getNextItem();	
		}
		BowlingTurn [] arr = list.toArray(new BowlingTurn[list.size()]);
		
		return arr;
	}

	@Override
	public BowlingTurn newTurn() {
		// TODO Auto-generated method stub
		BowlingTurn newTurn = new BowlingTurnImpl();
		BowlingTurn turn = this.getFirstTurn();
		while(turn.getAsLinkedNode().getNextItem()!=null) {
			turn = turn.getAsLinkedNode().getNextItem();
		}
		turn.getAsLinkedNode().setNextItem(newTurn);
		
		return newTurn;
	}

	@Override
	public Boolean isGameFinished() {
		// TODO Auto-generated method stub
		BowlingTurn [] turns = this.getTurns();
		Integer maxTurn = entity.getMaxTurn();
		if(turns.length == maxTurn + 1 && turns[maxTurn - 1].isMiss())
			return true;
		if(turns.length == maxTurn + 1 && turns[maxTurn - 1].isSpare()
				&& turns[maxTurn].getSecondPin() != null)
			return true;
		if(turns.length == maxTurn + 2 && turns[maxTurn + 1].getSecondPin() != null)
			return true;
		if(turns.length == maxTurn + 2 && !turns[maxTurn].isStrike())
			return true;
		if(turns.length > maxTurn + 2)
			return true;
		return false;
	}

	@Override
	public StatusCode addScores(Integer... pins) {
		// TODO Auto-generated method stub
		BowlingTurn bowlingTurn = this.getFirstTurn();
		while(bowlingTurn.getAsLinkedNode().getNextItem()!=null) {
			bowlingTurn = bowlingTurn.getAsLinkedNode().getNextItem();
		}
		Integer first = bowlingTurn.getFirstPin();
		Integer second = bowlingTurn.getSecondPin();
		StatusCode status = bowlingTurn.addPins(pins);
		
		if(status == StatusCodeImpl.FAIL) {
			bowlingTurn.getAsLinkedNode().setNextItem(null);
			bowlingTurn.getEntity().setFirstPin(first);
			bowlingTurn.getEntity().setSecondPin(second);
			return status;
		}
		else {
			if(this.isGameFinished()) {
				
				bowlingTurn.getAsLinkedNode().setNextItem(null);
				bowlingTurn.getEntity().setFirstPin(first);
				bowlingTurn.getEntity().setSecondPin(second);
				return status;
			}
			else {
				ArrayList<BowlingTurnEntity> list = new ArrayList<BowlingTurnEntity>();
				
//				for(BowlingTurn turn:this.getTurns()) {
//					
//					list.add(turn.getEntity());
//					
//					
//					
//				}

				BowlingTurn turn = this.getFirstTurn();
				while(turn != null) {
					list.add(turn.getEntity());
					turn = turn.getAsLinkedNode().getNextItem();	
				}
				
				
				BowlingTurnEntity [] arr = list.toArray(new BowlingTurnEntity[list.size()]);	
				this.entity.setTurnEntities(arr);
				
				return StatusCodeImpl.SUCCESS;
			}
		}
		
		
	}

	@Override
	public BowlingGameEntity getEntity() {
		// TODO Auto-generated method stub
		return entity;
	}

	@Override
	public BowlingTurn getFirstTurn() {
		// TODO Auto-generated method stub
		if(firstTurn != null)
			return firstTurn;
		else {
			firstTurn = new BowlingTurnImpl();
			ArrayList<BowlingTurnEntity> list = new ArrayList<BowlingTurnEntity>();
			list.add(firstTurn.getEntity());
			BowlingTurnEntity [] arr = list.toArray(new BowlingTurnEntity[list.size()]);
			
			this.entity.setTurnEntities(arr);
			return firstTurn;
		}
	}

}
