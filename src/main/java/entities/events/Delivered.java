package entities.events;

import entities.identifiers.GameId;
import entities.identifiers.NodeId;
import entities.interfaces.IEvent;

public record Delivered(
        GameId game,
        NodeId provider,
        NodeId client,
        int deliveryTime,
        int amount,
        int roundIndex
) implements IEvent {
}
