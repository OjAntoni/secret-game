package com.mygdx.game.actors.wilk;

import com.mygdx.game.messages.core.Coordinates;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WilkState {
    private Coordinates coordinates;
    private boolean isAlive;
}
