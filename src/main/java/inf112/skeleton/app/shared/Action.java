package inf112.skeleton.app.shared;

public enum Action {
    MOVE_ONE(300),
    MOVE_TWO(600),
    MOVE_THREE(900),
    ROTATE_LEFT(-90),
    ROTATE_RIGHT(90),
    U_TURN(180),
    BACK_UP(-300);

    private final float action;

    public float getAction(){
        return this.action;
    }
    Action(float action){

        this.action = action;
    }
}
