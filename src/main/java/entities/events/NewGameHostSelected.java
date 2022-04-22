package entities.events;

import entities.identifiers.GameId;
import entities.interfaces.IEvent;

public record NewGameHostSelected(
        GameId game
) implements IEvent {
}
