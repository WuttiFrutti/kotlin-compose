package entities.events;

import entities.identifiers.GameId;
import entities.identifiers.NodeId;
import entities.identifiers.UserId;
import entities.interfaces.IEvent;

public record PlayerJoinedGame(
        GameId game,
        UserId user,
        NodeId node,
        String agentConfig
) implements IEvent {
}
