package entities.events;

import entities.identifiers.GameId;
import entities.interfaces.IEvent;
import entities.objects.GameSettings;
import entities.objects.SupplyChainStructure;

import java.util.List;

public record ConnectToGameRequestAccepted(
        GameId game,
        GameSettings settings,
        SupplyChainStructure structure,
        List<IEvent> history
) implements IEvent {
}
