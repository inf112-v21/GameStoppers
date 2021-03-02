package inf112.skeleton.app.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector3;
import inf112.skeleton.app.cards.Card;
import inf112.skeleton.app.cards.CardDeck;
import inf112.skeleton.app.graphics.Graphics;
import inf112.skeleton.app.shared.Action;
import inf112.skeleton.app.shared.Direction;
import org.w3c.dom.ls.LSOutput;

import java.util.ArrayList;
import java.util.Arrays;


public class HumanPlayer extends Player implements InputProcessor {

    /**
     * This is the backend version of the human player. This class keeps track of everything related
     * to the human player. It also contain methods for matching players sprite in graphics class to the player.
     * As well as keeping track of players card.
     * @param direction The direction the player is facing
     * @param name The name of the player
     * @param piece The name of chosen piece, which is matched with a Sprite in graphics
     */
    public HumanPlayer(Direction direction, String name, String piece) {

        super(direction, name, piece);
    }
    //TODO move to new card class?
    private CardDeck cardDeck = new CardDeck();

    /**
     * Get the first player deck og card. This deck is automatically updated
     * by other methods and needs to be used only when starting the game.
     * @param player
     * @return
     */
    public ArrayList<Card> startDeckPlayer(HumanPlayer player){
        return cardDeck.dealNineCards();

    }

    /**
     * Needed to mach Sprite positions with back-end
     * When setting up player sprite in create method.
     * Pass this method in as X parameter in set player position sprite-built-in method.
     *
     * @param playerStartXPosition
     * @return
     */
    public float setPlayerStartXPosition(float playerStartXPosition){
        playerCurrentXPosition = playerStartXPosition;
        return playerStartXPosition;
    }
    /**
     * Needed to mach Sprite positions with back-end
     * When setting up player sprite in create method.
     * Pass this method in as X parameter in set player position sprite-built-in method.
     * @param playerStartYPosition
     * @return
     */
    public float setPlayerStartYPosition(float playerStartYPosition){
        playerCurrentYPosition = playerStartYPosition;
        return playerCurrentYPosition;
    }

    /**
     * Updates player X position and returns the same value, used in the render method of the
     * graphics class to keep track on player sprite and backend.
     * @return updated player x position
     */
    public float updatePlayerXPosition(float newXPosition){
        return playerCurrentXPosition = newXPosition;
    }

    /**
     * Updates player Y position and returns the same value, used in the render method of the
     * graphics class to keep track on player sprite and backend.
     * @return updated player y position
     */
    public float updatePlayerYPosition(float newYPosition){
        return playerCurrentYPosition = newYPosition;
    }

    /**
     *
     * @return x position of player : float
     */
    public float getPlayerXPosition() {
        return playerCurrentXPosition;
    }

    /**
     *
     * @return y position of player : float
     */
    public float getPlayerYPosition() {
        return playerCurrentYPosition;
    }


        //TODO move to new cardClass
    /**
     *  Checks if a card is a card that changes the position of a player
     *  and not the direction.
     * @param card : Card of a player
     * @return boolean
     */
    public boolean moveTypeCard(Card card){
        return card.action == Action.MOVE_ONE || card.action == Action.MOVE_TWO|| card.action == Action.MOVE_THREE||card.action== Action.BACK_UP;
    }

     //TODO dummy method to do rounds without priority used only for development.
     // Erlend will DELETE BEFORE DELIVERY
    public void round(HumanPlayer player){
        //System.out.println("NEW ROUND");

        if(dummyPlayerDeck ==4 && chosenCards.size()==5){
            updatePlayerLocation(player,chosenCards.get(0));
            updatePlayerLocation(player,chosenCards.get(1));
            updatePlayerLocation(player,chosenCards.get(2));
            updatePlayerLocation(player,chosenCards.get(3));
            updatePlayerLocation(player,chosenCards.get(4));
            dummyPlayerDeck = 9;
            chosenCards = new ArrayList<>();
            playerDeck = new ArrayList<>();
            playerDeck = cardDeck.dealNineCards();
            cardCoordinates =new ArrayList<Float>(
                    Arrays.asList(5555f,3090f,
                            6080f,3090f,
                            6605f,3090f,
                            5555f,2370f,
                            6080f,2370f,
                            6605f,2370f,
                            5555f,1640f,
                            6080f,1640f,
                            6605f,1640f));
        }
        /*
        ArrayList<Card> playerCard = cardDeck.CardDeal();
        for(int i = 0; i <9; i++){
            Card card = playerCard.get(i);
            //System.out.println(getX() + " old Xpos " + getY() + " old Y pos" );
            //System.out.println(player.direction + " oldDirection");
            //System.out.println(playerCard.get(i).action);
            updatePlayerLocation(player, card);
            //System.out.println(player.direction + " newDirection");
            //System.out.println(getX() + " new Xpos " + getY() + " new Y pos" );
            //System.out.println("  ");
        }*/
    }

    /**
     * Set new direction of the player related to the given card
     * @param player
     * @param cardDirection
     */
    public void setPlayerDirection(HumanPlayer player, Card cardDirection){
       float newDirection = player.direction.getDirection()+cardDirection.action.getAction();
       if(newDirection > 270) newDirection = newDirection - 360;
       if(newDirection < 0) newDirection = 270;
       if(newDirection == 0) player.direction = Direction.NORTH;
       else if (newDirection == 90) player.direction = Direction.EAST;
       else if (newDirection == 180) player.direction = Direction.SOUTH;
       else if (newDirection == 270) player.direction = Direction.WEST;
    }

    /**
     * Update player location or direction from player card
     * @param player
     * @param card
     */
    @Override
    public void updatePlayerLocation(HumanPlayer player, Card card) {
        if (moveTypeCard(card)) {
            if (player.direction == Direction.NORTH && canPlayerMove(getPlayerXPosition(), getPlayerYPosition()+card.action.getAction())) {
                updatePlayerYPosition(getPlayerYPosition()+card.action.getAction());
            } else if (player.direction == Direction.SOUTH && canPlayerMove(getPlayerXPosition(), getPlayerYPosition()-card.action.getAction())) {
                updatePlayerYPosition(getPlayerYPosition()-card.action.getAction());
            } else if (player.direction == Direction.EAST && canPlayerMove(getPlayerXPosition()+card.action.getAction(), getPlayerYPosition())) {
                updatePlayerXPosition(getPlayerXPosition()+card.action.getAction());
            } else if (player.direction == Direction.WEST && canPlayerMove(getPlayerXPosition()-card.action.getAction(), getPlayerYPosition())) {
                updatePlayerXPosition(getPlayerXPosition()-card.action.getAction());
            }
        } else if (!moveTypeCard(card)) {
            setPlayerDirection(player, card);
        }
    }

    //TODO move to new card class
    /**
     * Sets new position for the given card.
     * This function is used to move cards the player is holding
     * to the programming slots on the board in the correct order.
     * @param cardXPositionIndex X index of the card from cardCoordinates list
     * @param cardYPositionIndex Y index of the card from cardCoordinates list
     */
    public void updateCardPosition(int cardXPositionIndex, int cardYPositionIndex){
        int chosenCardListSize = chosenCards.size();
        if (chosenCardListSize < 6){
            cardCoordinates.set(cardYPositionIndex, 520f);
            if (chosenCardListSize == 1) cardCoordinates.set(cardXPositionIndex, 3945f);
            if (chosenCardListSize == 2) cardCoordinates.set(cardXPositionIndex, 4490f);
            if (chosenCardListSize == 3) cardCoordinates.set(cardXPositionIndex, 5020f);
            if (chosenCardListSize == 4) cardCoordinates.set(cardXPositionIndex, 5550f);
            if (chosenCardListSize == 5) cardCoordinates.set(cardXPositionIndex, 6090f);
        }else{
            cardCoordinates.set(cardXPositionIndex,cardCoordinates.get(cardXPositionIndex));
            cardCoordinates.set(cardYPositionIndex,cardCoordinates.get(cardYPositionIndex));
        }
    }

    /**
     * Check if the player is on a flag.
     * The flag layer is common for all players.
     * @param flagLayer
     * @return boolean
     */
    @Override
    public boolean isPlayerOnFlag(TiledMapTileLayer flagLayer) {
        TiledMapTileLayer.Cell cell = flagLayer.getCell(normalizedCoordinates(playerCurrentXPosition), normalizedCoordinates(playerCurrentYPosition));
        return cell!= null;
    }

    /**
     * Check if the player can move. For the time the player can move
     * if the player keeps within the board.
     * @param xDirection
     * @param yDirection
     * @return boolean
     */
    @Override
    public boolean canPlayerMove(float xDirection, float yDirection) {
        return !(xDirection < 0 || xDirection > 3300 || yDirection < 0 || yDirection > 3900);
    }

    /**
     * Calculate normalized coordinates. The tiled-map operates with
     * tile-size of 300 by 300. While the layers operates with tile-size of 1 by 1.
     * @param unNormalizedValue
     * @return int
     */
    @Override
    public int normalizedCoordinates(float unNormalizedValue) {
        return (int) unNormalizedValue/300;
    }

    //TODO move to game and UPDATE!
    @Override
    public boolean isGameOver(TiledMapTileLayer flagLayer) {
        return false;
    }


    /**
     * keyDown registers what happens when the key is pressed down.
     * Player move one tile upon down-press
     *
     * @param keyPressed
     * @return true : boolean
     */

    @Override
    public boolean keyDown(int keyPressed) {
        int tileDirection = 300;
        if (keyPressed == Input.Keys.LEFT) {
            if (keyPressed == Input.Keys.LEFT) {
                updatePlayerXPosition(playerCurrentXPosition - tileDirection);
            } else if (keyPressed == Input.Keys.RIGHT) {
                updatePlayerXPosition(playerCurrentXPosition + tileDirection);
            } else if (keyPressed == Input.Keys.UP) {
                updatePlayerYPosition(playerCurrentYPosition + tileDirection);
            } else if (keyPressed == Input.Keys.DOWN) {
                updatePlayerYPosition(playerCurrentYPosition - tileDirection);
            }
        }
        return true;
    }

    /**
     * keyUp registers what happens when the key is released.
     * Player to stop moving when button is released
     *
     * @param keyPressed
     * @return true : boolean
     */
    @Override
    public boolean keyUp(int keyPressed) {

        return false;
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }


    Vector3 touchPos = new Vector3();
    Graphics graphics;
    public float xhg;
    public float yhg;

    public void coordinates(OrthographicCamera camera){
        if (Gdx.input.isTouched()) {
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
        }
        if(Gdx.input.justTouched()){
            xhg = touchPos.x;
            yhg= touchPos.y;
            System.out.println(touchPos.x+ "just");
        }
    }

    /**
     * Create a click-box around the cards the player is holding.
     * When clicked the card sprite moves to the programming slots on the board
     * and backend part of the card i added to the chosen card list.
     *
     * @param i
     * @param i1
     * @param i2
     * @param i3
     * @return
     */
    //TODO need to be able to undo choice of card

    @Override
    public boolean touchDown(int i, int i1, int i2, int i3) {
     return false;
    }


    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) {
        float x  = xhg;
        float y = yhg;
        System.out.println(xhg + " X Pos");
        System.out.println(yhg +" Y pos");

        if (x >5555 && x < 6005 && y >= 3090 && y <= 3740){
            chosenCards.add(playerDeck.get(0));
            dummyPlayerDeck -=1;
            updateCardPosition(0,1);
        }
        else if(x >6080 && x < 6535 && y >= 3090 && y <= 3740){
            chosenCards.add(playerDeck.get(1));
            dummyPlayerDeck -= 1;
            updateCardPosition(2,3);
        }
        else if(x >6605 && x < 7060 && y >= 3090 && y <= 3740){
            chosenCards.add(playerDeck.get(2));
            dummyPlayerDeck -= 1;
            updateCardPosition(4,5);
        }
        else if(x >5555 && x < 6005 && y >= 2370 && y <= 3020){
            chosenCards.add(playerDeck.get(3));
            dummyPlayerDeck -=1;
            updateCardPosition(6,7);
        }
        else if(x >6080 && x < 6535 && y >= 2370 && y <= 3020){
            chosenCards.add(playerDeck.get(4));
            dummyPlayerDeck -=1;
            updateCardPosition(8,9);
        }
        else if(x >6605 && x < 7060 && y >= 2370 && y <= 3020){
            chosenCards.add(playerDeck.get(5));
            dummyPlayerDeck -= 1 ;
            updateCardPosition(10,11);
        }
        else if(x >5555 && x < 6005 && y >= 1640 && y <= 2290){
            chosenCards.add(playerDeck.get(6));
            dummyPlayerDeck -= 1 ;
            updateCardPosition(12,13);
        }
        else if(x >6080 && x < 6535 && y >= 1640 && y <= 2290){
            chosenCards.add(playerDeck.get(7));
            dummyPlayerDeck -= 1 ;
            updateCardPosition(14,15);
        }
        else if(x >6605 && x < 7060 && y >= 1640 && y <= 2290){
            chosenCards.add(playerDeck.get(8));
            dummyPlayerDeck -= 1 ;
            updateCardPosition(16,17);
        }
        return false;

    }


    @Override
    public boolean touchDragged(int i, int i1, int i2) {
        return false;
    }


    @Override
    public boolean mouseMoved(int i, int i1) {
        return false;
    }


    @Override
    public boolean scrolled(int i) {
        return false;
    }




}
