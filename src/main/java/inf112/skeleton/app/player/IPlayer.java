package inf112.skeleton.app.player;

import inf112.skeleton.app.GameLogic.GameLogic;
import inf112.skeleton.app.shared.Action;

public interface IPlayer {

    /**
     * Get a list of actions from the player
     * @return actions
     */
    Action[] getActions(GameLogic board);

    //TODO future deliveries will have damage tokens, health, player name, player piece etc.
}
