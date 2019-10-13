package training.adv.bowling.api;

import java.io.Serializable;

public interface Persistable<T extends Entity<? extends Serializable>> {
	T getEntity();
}
