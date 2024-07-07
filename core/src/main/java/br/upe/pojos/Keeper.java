package br.upe.pojos;

public interface Keeper {
    public static GreatEvent createGreatEvent(){
        return new GreatEvent();

    }
    public static SubEvent createSubEvent(){
        return new SubEvent();
    }
}
