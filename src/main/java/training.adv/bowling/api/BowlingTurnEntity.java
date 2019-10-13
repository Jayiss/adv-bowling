package training.adv.bowling.api;

public interface BowlingTurnEntity extends TurnEntity {

	Integer getFirstPin();
	Integer getSecondPin();
	
	void setFirstPin(Integer pin);
	void setSecondPin(Integer pin);

}
