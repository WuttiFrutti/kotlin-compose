package entities.events;

import entities.identifiers.GameId;
import entities.interfaces.IEvent;

public record GameStarted(
        GameId game
) implements IEvent {
}
