package training.adv.bowling.impl;

import java.io.Serializable;

import training.adv.bowling.api.Entity;
import training.adv.bowling.api.Persistable;

public abstract class AbstractDao<E extends Entity<K>, 
		D extends Persistable<E>, K extends Serializable> {
	
	public void save(D domain) {
		this.doSave(domain.getEntity());
	}
	protected abstract void doSave(E entity);
	public D load(K id) {
		E entity = this.doLoad(id);
		return doBuildDomain(entity);
	}
	protected abstract E doLoad(K id);
	protected abstract D doBuildDomain(E entity);
	public abstract boolean remove(K key);
}
