
public enum EnumCommands {
    PRESS_MOUSE(-1),
    RELEASE_MOUSE(-2),
    PRESS_KEY(-3),
    RELEASE_KEY(-4),
    MOVE_MOUSE(-5),
    DRAG_MOUSE(-6),
    SCROLL_MOUSE(-7);

    private int get;

    EnumCommands(int v){get = v;}

    public int getAbbrev(){
        return get;
    }
}
