package entities.events;

import entities.identifiers.GameId;
import entities.identifiers.UserId;
import entities.interfaces.IEvent;

public record PlayerLeftGame(
        GameId game,
        UserId user
) implements IEvent {
}
