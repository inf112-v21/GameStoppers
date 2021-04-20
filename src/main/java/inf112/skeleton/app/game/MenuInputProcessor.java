package inf112.skeleton.app.game;

import inf112.skeleton.app.graphics.Graphics;

import java.net.InetAddress;

public class MenuInputProcessor extends BaseInputProcessor {
    final Graphics graphics;
    String ip = "";

    public MenuInputProcessor(Graphics graphics) {
        super();
        this.graphics = graphics;
    }

    @Override
    public boolean keyDown(int i) {
        return false;
    }

    @Override
    public boolean keyUp(int i) {
        return false;
    }

    @Override
    public boolean keyTyped(char c) {
        System.out.println("Character typed: " + c);

        try {
            int i = Integer.parseInt(c + "");
            ip += i;
            System.out.println("Total ip so far is " + ip);
        } catch (Exception e) {
            if (c == '.') {
                ip += c;
            } else {
                ip = "";
            }
        }
        return false;
    }

    @Override
    public boolean touchDown(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) {
        float x = mouseClickXCoordinate;
        float y = mouseClickYCoordinate;

        System.out.println("Touch in menu input " + x + ", " + y);
        if (x >= 3260 && x <= 3960) {
            if(y <= 2653 && y >= 2415) {
                System.out.println("Single player click");
                graphics.game.typeOfGameStarted = GameType.SINGLE_PLAYER;
                graphics.game.currentScreen = GameScreen.GAME;

            } else if (y <= 1950 && y >= 1722) {
                System.out.println("Host click");
                graphics.game.currentScreen = GameScreen.HOST;


            } else if(y <= 1240 && y >= 1036) {
                System.out.println("Join click, attempting to join ip" + ip);
                try {
                    InetAddress hostIp = InetAddress.getByName(ip);
                    graphics.game.typeOfGameStarted = GameType.NETWORK_JOIN;
                    graphics.game.joinNewGame(hostIp);
                } catch (Exception e) {

                }
            }
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
