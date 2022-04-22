package entities.events;

import entities.identifiers.GameId;
import entities.interfaces.IEvent;

public record JoinGameRequestAccepted(
        GameId game
) implements IEvent {
}
