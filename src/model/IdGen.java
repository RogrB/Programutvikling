package model;

public class IdGen {
    private static IdGen inst = new IdGen();
    private IdGen(){}
    public static IdGen getInstance(){ return inst; }
}
