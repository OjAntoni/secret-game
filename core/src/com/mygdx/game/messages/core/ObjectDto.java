package com.mygdx.game.messages.core;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ObjectDto {
    private int id;
    private String name;
    private Coordinates coordinates;
}
