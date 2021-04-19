package inf112.skeleton.app.graphics;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import inf112.skeleton.app.card.CardMoveLogic;
import inf112.skeleton.app.game.Game;
import inf112.skeleton.app.game.GameScreen;
import inf112.skeleton.app.game.GameType;
import inf112.skeleton.app.game.MenuInputProcessor;
import inf112.skeleton.app.player.HumanPlayer;
import inf112.skeleton.app.player.Player;
import inf112.skeleton.app.screens.EndScreen;
import inf112.skeleton.app.shared.Action;
import inf112.skeleton.app.shared.Color;
import inf112.skeleton.app.shared.Direction;

import java.util.ArrayList;
import java.util.HashMap;

public class Graphics implements ApplicationListener {
    public TiledMap tiledMap;

    private OrthographicCamera camera;
    private OrthogonalTiledMapRenderer tiledMapRenderer;
    private SpriteBatch spriteBatch;
    public Texture background;
    public Texture menuScreenBackground;
    public Texture youWin, youLose;
    public Texture exitButton, returnButton;
    public Texture reset, notReset;
    public Texture ready, notReady;
    public Texture emptyLifeToken, lifeToken1, lifeToken2, lifeToken3;
    public Texture singlePlayerButton;
    public Texture joinMultiPlayerButton;
    public Texture hostMultiPlayerButton;
    public PlayerGraphics playerGraphics;
    public final CardGraphics cardGraphics;
    public final HumanPlayer singlePlayer = new HumanPlayer(Direction.NORTH,69,Color.GREEN);
    private final MenuInputProcessor menuInputProcessor;
    private final EndScreen endScreen;
    public Sprite singlePlayerSprite;
    public ArrayList<Sprite> cardSpriteList;
    private final CardMoveLogic cardMoveLogic = new CardMoveLogic();
    private HashMap<Action, Texture> cardTextures = new HashMap<>();
    public final Game game;
    public final ArrayList<Player> singlePlayerList =new ArrayList<>();


    public Graphics(Game game) {
        menuInputProcessor = new MenuInputProcessor(this);
        endScreen = new EndScreen(this);
        cardGraphics = new CardGraphics();
        this.game = game;
    }

    public void updateCardSprite(Player humanPlayer) {
        int cardNumber = 0;
        int cardCoordinateX = 0;
        int cardCoordinateY = 1;
        for (Sprite card: cardSpriteList) {
            card.setSize(455, 650);
            card.setPosition(humanPlayer.cardCoordinates.get(cardCoordinateX),humanPlayer.cardCoordinates.get(cardCoordinateY));
            card.setTexture(cardTextures.get(humanPlayer.playerDeck.get(cardNumber).action));
            card.draw(tiledMapRenderer.getBatch());
            cardNumber++;
            cardCoordinateX += 2;
            cardCoordinateY += 2;
        }
    }


    public void setInputProcessor(Player player){
        Gdx.input.setInputProcessor((InputProcessor) player);
    }


    public void updatePlayerSprite(ArrayList<Player> players){
        if (players == null || players.isEmpty()) {
            // No players created yet, don't render any
            return;
        }
        for (Player player : players){
            player.setMouseClickCoordinates(camera);
            setInputProcessor(player);
            updateCardSprite(player);

            Sprite playerSprite = playerGraphics.getPlayerSprite(player);
            Texture playerTexture = playerGraphics.getPlayerTexture(player);
            playerSprite.setTexture(playerTexture);

            playerSprite.setSize(300,300);
            playerSprite.setPosition(player.getPlayerXPosition(), player.getPlayerYPosition());
            playerSprite.draw(tiledMapRenderer.getBatch());
        }
    }

    /**
     * temp runs a single-player gui
     */
    public void singlePlayer(){
        singlePlayer.setMouseClickCoordinates(camera);
        updateCardSprite(singlePlayer);

        singlePlayerSprite.setPosition(singlePlayer.getPlayerXPosition(), singlePlayer.getPlayerYPosition());
        Texture playerTexture = playerGraphics.getPlayerTexture(singlePlayer);
        singlePlayerSprite.setTexture(playerTexture);
        singlePlayerSprite.draw(tiledMapRenderer.getBatch());
        singlePlayer.singlePlayerRound(singlePlayerList,
                (TiledMapTileLayer) tiledMap.getLayers().get("Laser"),
                (TiledMapTileLayer) tiledMap.getLayers().get("BlueConveyor"),
                (TiledMapTileLayer) tiledMap.getLayers().get("YellowConveyor"),
                (TiledMapTileLayer) tiledMap.getLayers().get("RedGear"),
                (TiledMapTileLayer) tiledMap.getLayers().get("GreenGear"),
                (TiledMapTileLayer) tiledMap.getLayers().get("Hole"));

    }

    @Override
    public void create() {
        playerGraphics = new PlayerGraphics();
        singlePlayer.playerDeck = cardMoveLogic.playerDeck();

        singlePlayerSprite = playerGraphics.getPlayerSprite(singlePlayer.color);
        singlePlayerSprite.setSize(300,300);

        // Creates a list of sprites
        cardSpriteList = cardGraphics.createCardSprite();
        cardTextures = cardGraphics.createCardTexture();

        singlePlayerList.add(singlePlayer);
        float w = 600;
        float h = 1000;
        spriteBatch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.zoom = 7f; //Shows more of the board
        camera.setToOrtho(false, h, w); //something needs adjustment here
        camera.update();
        tiledMap = new TmxMapLoader().load("Maps/RiskyExchange.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        background = new Texture("Background.png");
        youWin = new Texture("YouWin.png");
        youLose = new Texture("YouLose.png");

        //reset and ready textures
        reset = new Texture("Buttons/RESET.png");
        notReset = new Texture("Buttons/notRESET.png");
        ready = new Texture("Buttons/READY.png");
        notReady = new Texture("Buttons/notREADY.png");

        lifeToken3 = new Texture("LifeToken/1.png");
        lifeToken2 = new Texture("LifeToken/2.png");
        lifeToken1 = new Texture("LifeToken/3.png");
        emptyLifeToken = new Texture("LifeToken/4.png");

        // Menu Textures
        menuScreenBackground = new Texture("MenuScreen/MenuBackground.png");
        singlePlayerButton = new Texture("SinglePlayerButton1.png");
        joinMultiPlayerButton = new Texture("JoinGameButton1.png");
        hostMultiPlayerButton = new Texture("HostGameButton1.png");

        //End screen textures
        exitButton = new Texture("ExitButton.png"); //TODO might change later
        returnButton = new Texture("ReturnButton.png");
    }

    /**
     * Displayed on the screen.
     * @param width of the screen
     * @param height of the screen
     */
    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = 1280;
        camera.viewportHeight = 720;
        camera.update();
    }

    private void renderMenuScreen() {
        // Draw background
        spriteBatch.begin();
        spriteBatch.draw(menuScreenBackground, 0, 0, 1280, 720);
        spriteBatch.end();

        // Draw buttons
        spriteBatch.begin();
        spriteBatch.draw(singlePlayerButton, 530, 350, 250, 150);
        spriteBatch.draw(hostMultiPlayerButton, 530, 250, 250, 150);
        spriteBatch.draw(joinMultiPlayerButton, 530, 150, 250, 150);
        spriteBatch.end();

        // Handle inputs
        menuInputProcessor.setMouseClickCoordinates(camera);
        Gdx.input.setInputProcessor(menuInputProcessor);
    }

    private void renderHostGameScreen(){
        //draw background
        spriteBatch.begin();
        spriteBatch.draw(menuScreenBackground, 0, 0, 1280, 720);
        spriteBatch.end();

        //draw ip address
        String ip = "192.92.39.102"; // TODO actual ip

        //draw buttons


        //handle input
        menuInputProcessor.setMouseClickCoordinates(camera);
        Gdx.input.setInputProcessor(menuInputProcessor);
    }

    private void renderWin() {
        spriteBatch.begin();
        spriteBatch.draw(youWin, 0, 0, 1280, 720);
        spriteBatch.end();

        spriteBatch.begin();
        spriteBatch.draw(returnButton, 530, 250, 250, 150);
        spriteBatch.end();

        spriteBatch.begin();
        spriteBatch.draw(exitButton, 530, 150, 250, 150);
        spriteBatch.end();

        endScreen.setMouseClickCoordinates(camera);
        Gdx.input.setInputProcessor(endScreen);
    }

    private void renderLose() {
        spriteBatch.begin();
        spriteBatch.draw(youLose, 0, 0, 1280, 720);
        spriteBatch.end();

        spriteBatch.begin();
        spriteBatch.draw(returnButton, 530, 250, 250, 150);
        spriteBatch.end();

        spriteBatch.begin();
        spriteBatch.draw(exitButton, 530, 150, 250, 150);
        spriteBatch.end();

        endScreen.setMouseClickCoordinates(camera);
        Gdx.input.setInputProcessor(endScreen);
    }

    /**
     * This is where the graphics of the game get rendered.
     */
    @Override
    public void render() {
        if (game.currentScreen == GameScreen.MENU) {
            renderMenuScreen();
            return;
        }

        spriteBatch.begin();
        spriteBatch.draw(background, 0, 0, 1280, 720);
        spriteBatch.end();

        //shows when to click the buttons and when not to click
        readyButtonIndicator();
        resetButtonIndicator();

        //informs how many lifes the player has left
        lifeTokenIndicator();

        camera.update();
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
        tiledMapRenderer.getBatch().begin();
        if (game.typeOfGameStarted == GameType.SINGLE_PLAYER) {
            Gdx.input.setInputProcessor(singlePlayer);
            singlePlayer();

        } else {
            updatePlayerSprite(game.players);
        }
        tiledMapRenderer.getBatch().end();

        if (singlePlayer.hasPlayerVisitedAllFlags((TiledMapTileLayer) tiledMap.getLayers().get("flagLayer"))) {
            if(game.winScreen== GameScreen.WIN) {
                //System.out.println("You Won!");
                renderWin();
            }
        }
    }

    public void readyButtonIndicator(){
        if(singlePlayer.movedCards.size() >= 5){
            spriteBatch.begin();
            spriteBatch.draw(ready, 1053, 175, 125, 55);
            spriteBatch.end();
        } else {
            spriteBatch.begin();
            spriteBatch.draw(notReady, 1053, 175, 125, 55);
            spriteBatch.end();
        }
    }

    public void resetButtonIndicator(){
        if(singlePlayer.movedCards.size() > 0){
            spriteBatch.begin();
            spriteBatch.draw(reset, 1053, 129, 125, 55);
            spriteBatch.end();
        } else {
            spriteBatch.begin();
            spriteBatch.draw(notReset, 1053, 129, 125, 55);
            spriteBatch.end();
        }
    }

    public void lifeTokenIndicator(){
        // displaying the lifetoken
        spriteBatch.begin();
        if(singlePlayer.getPlayerHealth() == 1){
            spriteBatch.draw(lifeToken1, 760, 535, 110, 65);
        }
        else if(singlePlayer.getPlayerHealth() == 2){
            spriteBatch.draw(lifeToken2, 760, 535, 110, 65);
        }
        else if(singlePlayer.getPlayerHealth() == 3){
            spriteBatch.draw(lifeToken3, 760, 535, 110, 65);
        }
        else{
            spriteBatch.draw(emptyLifeToken, 760, 535, 110, 65);
        }
        spriteBatch.end();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    /**
     * Disposes the window mode.
     */
    @Override
    public void dispose() {
        tiledMap.dispose();
        spriteBatch.dispose();
        background.dispose();
        youWin.dispose();
    }
}