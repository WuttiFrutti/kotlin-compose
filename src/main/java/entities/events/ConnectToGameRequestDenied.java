package entities.events;

import entities.identifiers.GameId;
import entities.interfaces.IEvent;

public record ConnectToGameRequestDenied(
        GameId game,
        String reason
) implements IEvent {
}
