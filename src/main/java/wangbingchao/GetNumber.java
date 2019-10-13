package wangbingchao;

public class GetNumber {
	private static int turnCount = 0;
	private static int gameCount = 10000;
	private int turnNum;
	private int gameNum;
//	public GetNumber() {
//		++turnCount;
//		++gameCount;
//		turnNum = turnCount;
//		gameNum = gameCount;
//	}
	public int getTurnNum() {
		return ++turnCount;
	}
	public int getGameNum() {
		return ++gameCount;
	}
}
