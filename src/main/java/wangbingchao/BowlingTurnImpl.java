package wangbingchao;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;

import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.LinkedList;
import training.adv.bowling.api.StatusCode;

public class BowlingTurnImpl implements BowlingTurn {

	

	BowlingTurnEntity entity;
	LinkedList linkedList;
	public BowlingTurnImpl() {
		this.entity = new BowlingTurnEntityImpl();
		this.linkedList = new LinkedListImpl(this);
	}
	public BowlingTurnImpl(BowlingTurnEntity entity) {
		this.entity = entity;
		this.linkedList = new LinkedListImpl(this);
	}
	@Override
	public Boolean isFinished() {
		// TODO Auto-generated method stub
		if(entity.getSecondPin()!=null)
			return true;
		else if(this.isStrike())
			return true;
		else
			return false;
	}

	@Override
	public Integer getScore() {
		// TODO Auto-generated method stub
		BowlingTurn nextTurn = this.getAsLinkedNode().getNextItem();
		if(this.isStrike() && nextTurn != null) {
			if(nextTurn.isStrike())
				return this.getFirstPin()+nextTurn.getFirstPin()
				+(nextTurn.getAsLinkedNode().getNextItem() == null ? 0 
						: nextTurn.getAsLinkedNode().getNextItem().getFirstPin());
			else
				return this.getFirstPin()+nextTurn.getFirstPin()+
						(nextTurn.getSecondPin() == null ? 0 : nextTurn.getSecondPin());
		}
		else if(this.isSpare() && nextTurn != null)
			return this.getFirstPin()+this.getSecondPin()+nextTurn.getFirstPin();
		else
			return (this.getFirstPin()== null ? 0 : this.getFirstPin())
					+(this.getSecondPin() == null ? 0 : this.getSecondPin());
	}

	@Override
	public Boolean isValid() {						//判断单回合内分数和是否合理 还是判断回合是否超过
		// TODO Auto-generated method stub
		if(this.getFirstPin() == null)
			return true;
		if(this.getFirstPin() > 10 || this.getFirstPin() < 0)
			return false;
		if(this.getSecondPin() != null) {
			if(this.getSecondPin() > 10 || this.getSecondPin() < 0)
				return false;
			if(this.getFirstPin() + this.getSecondPin() > 10)
				return false;
		}
		return true;
	}

	@Override
	public BowlingTurnEntity getEntity() {
		// TODO Auto-generated method stub
		return entity;
	}

	@Override
	public Boolean isStrike() {
		// TODO Auto-generated method stub
		if(this.getFirstPin() != null
				&& this.getFirstPin() == 10)
			return true;
		else
			return false;
	}

	@Override
	public Boolean isSpare() {
		// TODO Auto-generated method stub
		if(this.getFirstPin() != null && this.getSecondPin() != null
				&&this.getFirstPin()+this.getSecondPin() == 10)
			return true;
		else
			return false;
	}

	@Override
	public Boolean isMiss() {
		// TODO Auto-generated method stub
		if(!this.isSpare() && !this.isStrike())
			return true;
		else
			return false;
	}

	@Override
	public Integer getFirstPin() {
		// TODO Auto-generated method stub
		return entity.getFirstPin();
	}

	@Override
	public Integer getSecondPin() {
		// TODO Auto-generated method stub
		return entity.getSecondPin();
	}

	@Override
	public StatusCode addPins(Integer... pins) {
		// TODO Auto-generated method stub
		int range = 0;

		for(int i : pins) {
			
			if(this.isFinished()) {
				
				BowlingTurn newTurn = new BowlingTurnImpl();
				this.getAsLinkedNode().setNextItem(newTurn);
				StatusCode status = newTurn.addPins(Arrays.copyOfRange(pins, range, pins.length));
				if(status == StatusCodeImpl.FAIL)
					return status;
			}
			else {
				if(this.getFirstPin() == null) {				
					this.entity.setFirstPin(i);
					range++;
				}
				else {
					this.entity.setSecondPin(i);
					range++;
				}
			}
			
			if(!this.isValid()) {
				
				return StatusCodeImpl.FAIL;	
			}
		}
		return StatusCodeImpl.SUCCESS;
	}

	@Override
	public LinkedList<BowlingTurn> getAsLinkedNode() {
		// TODO Auto-generated method stub
		return linkedList;
	}
	
	public BowlingTurn clone() {
        BowlingTurn turn = null;
        try {
        	// 写入字节流
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(this);

            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            turn = (BowlingTurn) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return turn;
    }


}
