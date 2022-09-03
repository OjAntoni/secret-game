package com.mygdx.game.actors.jstar;

import com.mygdx.game.messages.core.Coordinates;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class JstarState {
    private Coordinates coordinates;
    private boolean normalJstar;
    private boolean stoppedJstar;
}
