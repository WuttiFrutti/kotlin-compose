package entities.events;

import entities.identifiers.GameId;
import entities.identifiers.NodeId;
import entities.interfaces.IEvent;

public record StockLost(
        GameId game,
        NodeId provider,
        NodeId client,
        int amount,
        int roundIndex
) implements IEvent {
}
