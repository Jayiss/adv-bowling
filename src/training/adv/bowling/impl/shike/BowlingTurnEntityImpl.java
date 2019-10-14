package training.adv.bowling.impl.shike;

import training.adv.bowling.api.BowlingGameEntity;
import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.TurnKey;

public class BowlingTurnEntityImpl implements BowlingTurnEntity{
	
	TurnKey id;
	Integer fpin;
	Integer spin;

	@Override
	public TurnKey getId() {return this.id;
	}

	@Override
	public void setId(TurnKey id) {this.id=id;
		
	}

	@Override
	public Integer getFirstPin() {return this.fpin;
	}

	@Override
	public Integer getSecondPin() {return this.spin;
	}

	@Override
	public void setFirstPin(Integer pin) {this.fpin=pin;
		
	}

	@Override
	public void setSecondPin(Integer pin) {this.spin=pin;
		
	}
	


}
