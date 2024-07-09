package br.upe.controllers;

import br.upe.pojos.Keeper;
import br.upe.pojos.GreatEvent;
import br.upe.pojos.PlainEvent;
import br.upe.pojos.SubEvent;

import java.util.ArrayList;

public class TestController {
    public static void main(String[] args){

        Keeper keeper = new Keeper();
        GreatEvent even1 = keeper.createGreatEvent();

        even1.setName("SUPER");
        even1.setSubEvents( new ArrayList<PlainEvent>() );

        SubEvent HansenAI = keeper.createSubEvent();
        HansenAI.setName("HansenAI2");

        even1.getSubEvents().add( HansenAI );

        System.out.println(even1.getSubEvents().iterator().next().getName());
    }
}
