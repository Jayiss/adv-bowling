package training.adv.bowling.impl.shike;

import java.util.ArrayList;

import training.adv.bowling.api.BowlingGameEntity;
import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.LinkedList;
import training.adv.bowling.api.StatusCode;
import training.adv.bowling.api.TurnKey;

public class BowlingTurnImpl implements BowlingTurn{
	
	BowlingTurnEntity turnEntity;
	
	Integer maxPin;
	Integer maxTurn;
	
	TurnKey turnKey;
	Integer firstPin=null;
	Integer secondPin=null;
	LinkedList<BowlingTurn> linkedList;
	
	public BowlingTurnImpl(Integer maxPin, Integer maxTurn,BowlingTurnEntity bowlingTurnEntity) {
		this.turnEntity=new BowlingTurnEntityImpl();
		this.maxPin=maxPin;
		this.maxTurn=maxTurn;
		this.turnEntity = bowlingTurnEntity;
		linkedList = new LinkedListImpl(this);
		
//		this.turnKey=turnEntity.getId();
//		this.firstPin=turnEntity.getFirstPin();
//		this.secondPin=turnEntity.getSecondPin();
//		linkedList = new LinkedListImpl(this);
		
	}
	public BowlingTurnImpl(BowlingTurnEntity bowlingTurnEntity) {
		turnEntity=bowlingTurnEntity;
		linkedList = new LinkedListImpl(this);
		this.maxPin=10;
		this.maxTurn=10;
		
	}
	

	@Override
	public Integer getScore() {
		if (!isFinished()) {
			System.out.println("not finished");
			return getFirstPin();
		}else {
			if (isMiss()) {
				System.out.println("isMiss");
				return getFirstPin()+getSecondPin();
			}
			
			if (isSpare() ) {
				System.out.println("isSpare");
				if (getAsLinkedNode().getNextItem()==null) {
					return getFirstPin()+getSecondPin();
				}else {
					return getFirstPin()+getSecondPin()+getAsLinkedNode().getNextItem().getFirstPin();
				}
			}
			
			if (isStrike()) {
				System.out.println("isStrike");
				if (getAsLinkedNode().getNextItem()==null) {
					return maxPin;
				}else if (!getAsLinkedNode().getNextItem().isStrike()) {
					if (getAsLinkedNode().getNextItem().isFinished()) {
						return maxPin+getAsLinkedNode().getNextItem().getFirstPin()+getAsLinkedNode().getNextItem().getSecondPin();
					}else {
						return maxPin+getAsLinkedNode().getNextItem().getFirstPin();
					}
					
				}else {
					if (getAsLinkedNode().getNextItem().getAsLinkedNode().getNextItem()==null) {
						return maxPin+maxPin;
					}else {
						return maxPin+maxPin+getAsLinkedNode().getNextItem().getAsLinkedNode().getNextItem().getFirstPin();
					}
				}
			}
		}
		

		return 0;
	}
	

	
	@Override
	public StatusCode addPins(Integer... pins) {
		if (pins.length==0) {
			return StatusCodeImpl.FAIL;
		}
		
		//finished
		if (isFinished()) {
			LinkedList<BowlingTurn> nowLinkedNode=getAsLinkedNode();
			for (int i = 0; i < pins.length; i++) {
				BowlingTurn bt = new BowlingTurnImpl(new BowlingTurnEntityImpl());
				
				if (i!=pins.length-1) {
					if (pins[i]==maxPin) {
						bt.getEntity().setFirstPin(pins[i]);
					}else {
						bt.getEntity().setFirstPin(pins[i]);
						bt.getEntity().setSecondPin(pins[i+1]);
						i++;
					}
				}else {
					bt.getEntity().setFirstPin(pins[i]);
				}
				LinkedList<BowlingTurn> linkedNode = new LinkedListImpl(bt);
				nowLinkedNode.setNextItem(bt);
				nowLinkedNode = bt.getAsLinkedNode();
			}
		}else {
			getEntity().setSecondPin(pins[0]);
			LinkedList<BowlingTurn> nowLinkedNode=getAsLinkedNode();
			for (int i = 1; i < pins.length; i++) {
				BowlingTurn bt = new BowlingTurnImpl(new BowlingTurnEntityImpl());
				if (i!=pins.length-1) {
					if (pins[i]==maxPin) {
						bt.getEntity().setFirstPin(pins[i]);
					}else {
						bt.getEntity().setFirstPin(pins[i]);
						bt.getEntity().setSecondPin(pins[i+1]);
						i++;
					}
				}else {
					bt.getEntity().setFirstPin(pins[i]);
				}
				LinkedList<BowlingTurn> linkedNode = new LinkedListImpl(bt);
				nowLinkedNode.setNextItem(bt);
				nowLinkedNode = linkedNode;
			}

		}
		
		//判断每个turn是否有效
		LinkedList<BowlingTurn> nowLinkedNode=getAsLinkedNode();
		LinkedList<BowlingTurn> nextNode;
		while (nowLinkedNode.getNextItem()!=null) {
			nextNode = nowLinkedNode.getNextItem().getAsLinkedNode();
			if (!nowLinkedNode.getNextItem().isValid()) {
				return StatusCodeImpl.FAIL;
			}
			nowLinkedNode=nextNode;
		}
		return StatusCodeImpl.SUCCESS;
		
	}

	@Override
	public Boolean isValid() {
		//是否有负数
		//加起来是否大于maxPin
		if (getFirstPin()==null && getSecondPin()==null) {
			return false;
		}
		if (getSecondPin()==null) {
			
			if (getFirstPin()>maxPin || getFirstPin()<0) {
				return false;
			}
		}else {
			if (getFirstPin()>maxPin || getFirstPin()<0 ||getSecondPin()>maxPin || getSecondPin()<0) {
				return false;
			}
			if (getFirstPin()+getSecondPin()>maxPin) {
				return false;
			}
		}
		return true;
	}

	@Override
	public BowlingTurnEntity getEntity() {return this.turnEntity;}

	@Override
	public Boolean isFinished() {
		if(turnEntity.getSecondPin() != null)
			return true;
		if(isStrike())
			return true;
		if (turnEntity.getFirstPin()==null && turnEntity.getSecondPin()==null) {
			return true;
		}
		return false;
	}
	
	@Override
	public Boolean isStrike() {
		if(getFirstPin() == maxPin)
			return true;
		return false;
	}

	@Override
	public Boolean isSpare() {
		if(getFirstPin() == null ||getSecondPin() == null)
			return false;
		if(getFirstPin()+getSecondPin() == maxPin && !isStrike())
			return true;
		return false;
	}

	@Override
	public Boolean isMiss() {
		if(!isSpare() && !isStrike() &isFinished())
			return true;
		return false;
	}

	@Override
	public Integer getFirstPin() {return turnEntity.getFirstPin();}

	@Override
	public Integer getSecondPin() {return turnEntity.getSecondPin();}



	@Override
	public LinkedList<BowlingTurn> getAsLinkedNode() { //返回添加了pins之后的链表
		return linkedList;
	}

}
