package inf112.skeleton.app.networking.packets;

import inf112.skeleton.app.card.Card;
import inf112.skeleton.app.player.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class Packets {
    public static class MessagePacket {
        public String message;
        public int playerID;
    }


    public static class PlayerNumberPacket{
        public int numberOfPlayers;
    }

    public static class StartSignalPacket{
        public boolean start;
    }

    public static class NamePacket{
        public String[] name;
        public int playerId;
    }

    public static class ReadySignalPacket{
        public boolean signal;
        public boolean[] allReady;
    }

    public static class ShutDownRobotPacket{
        public boolean[] playersShutdown;
    }

    public static class RemovePlayerPacket {
        public int playerId;
    }


    /** Packet containing a players moves, to be sent to the server */
    public static class CardsPacket {
        public ArrayList<Card> playedCards;
        public int playerId;
    }

    /** A packet containing all player moves for a round so that clients may execute them */
    public static class RoundPacket {
        public HashMap<Integer, ArrayList<Card>> playerMoves;
    }


    /** Package instructing all clients to start the game */
    public static class StartGamePackage {
    }





}
