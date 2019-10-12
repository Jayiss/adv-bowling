package training.adv.bowling.impl.lliushiying;

import training.adv.bowling.api.BowlingGame;


public class BowlingGameFactoryImpl {

	private static BowlingGameFactoryImpl factory=new BowlingGameFactoryImpl();
	private Integer index=1002;
	
	private BowlingGameFactoryImpl(){
		
	}
	
	public static BowlingGameFactoryImpl getInstance(){
		return factory;
	}
	

	public BowlingGame getGame() {
		//TODO:改成可扩展性
		BowlingGameImpl bowlingGameImpl=new BowlingGameImpl(new BowlingGameEntityImpl(index,10,10));
		index++;
		return bowlingGameImpl;
	}

}
