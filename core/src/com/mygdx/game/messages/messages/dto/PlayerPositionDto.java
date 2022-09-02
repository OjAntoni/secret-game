package com.mygdx.game.messages.messages.dto;

import com.mygdx.game.messages.core.Coordinates;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class PlayerPositionDto{
    public Coordinates coordinates;
    public int playerId;
}
