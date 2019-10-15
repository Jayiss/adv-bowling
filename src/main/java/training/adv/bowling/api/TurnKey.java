package training.adv.bowling.api;

import java.io.Serializable;

public interface TurnKey extends Serializable {
	Integer getId();
	Integer getForeignId();
}
