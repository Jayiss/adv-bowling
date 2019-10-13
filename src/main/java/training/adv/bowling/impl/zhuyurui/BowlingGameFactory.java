package training.adv.bowling.impl.zhuyurui;

import training.adv.bowling.api.BowlingGame;


public class BowlingGameFactory  {


    private BowlingGame bowlingGame=new BowlingGameImpl();

    private static BowlingGameFactory factory=new BowlingGameFactory();
    private Integer index=1002;

    private BowlingGameFactory(){

    }

    public static BowlingGameFactory getInstance(){
        return factory;
    }


    public BowlingGame getGame(Integer maxPin,Integer maxTurn) {
        BowlingGameImpl bowlingGameImpl=new BowlingGameImpl(new BowlingGameEntityImpl(index,maxPin,maxTurn));
        index++;
        return bowlingGameImpl;
    }
}
