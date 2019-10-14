package training.adv.bowling.impl.shike;

public class GetNumber {
	private static int turnCount = 0;
	private static int gameCount = 10000;
	
	public int getTurnNum() {
		return ++turnCount;
	}
	public int getGameNum() {
		return ++gameCount;
	}
}
