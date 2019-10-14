
package training.adv.bowling.api;



public interface GameFactory<G extends Game> {

	G getGame();

}
