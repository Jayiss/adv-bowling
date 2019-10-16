package training.adv.bowling.api;

import java.io.Serializable;

public interface TurnKey extends Serializable {
	String getId();
	String getForeignId();
}
