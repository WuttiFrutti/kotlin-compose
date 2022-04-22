package entities.events;

import entities.identifiers.GameId;
import entities.identifiers.NodeId;
import entities.interfaces.IEvent;

public record OrderDelayed(
        GameId game,
        NodeId provider,
        NodeId client,
        int deliveryTime,
        int roundIndex
) implements IEvent {
}
