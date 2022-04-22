package entities.events;

import entities.identifiers.GameId;
import entities.interfaces.IEvent;

import java.util.List;

public record GameRoundProcessed(
        GameId game,
        List<IEvent> events,
        int roundIndex
) implements IEvent {
}
