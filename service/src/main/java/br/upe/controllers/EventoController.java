package br.upe.controllers;

import br.upe.pojos.PlainEvent;
import br.upe.pojos.Keeper;

////////////////////////////////////////////////////////////////////////////////////
public class EventoController {
    public static void main(String[] args){
        PlainEvent even1 = Keeper.createGreatEvent();

        even1.setName("SUPER");

        System.out.println(even1.getName());
    }
}
