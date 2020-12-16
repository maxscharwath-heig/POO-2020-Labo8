package engine.utils;

public class Position {
    public int x;
    public int y;

    public Position(){
        this(0,0);
    }

    public Position(int x, int y){
        set(x,y);
    }

    public Position set(int x, int y){
        this.x = x;
        this.y = y;
        return  this;
    }
}
