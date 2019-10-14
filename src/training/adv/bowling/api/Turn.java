package training.adv.bowling.api;

public interface Turn {
	Boolean isFinished();
	Integer getScore();
	Boolean isValid();
}
