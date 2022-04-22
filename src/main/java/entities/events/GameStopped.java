package entities.events;

import entities.identifiers.GameId;
import entities.interfaces.IEvent;

public record GameStopped(
        GameId game
) implements IEvent {
}
