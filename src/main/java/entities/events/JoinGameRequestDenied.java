package entities.events;

import entities.identifiers.GameId;
import entities.interfaces.IEvent;

public record JoinGameRequestDenied(
        GameId game,
        String reason
) implements IEvent {
}
