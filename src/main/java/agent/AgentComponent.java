package agent;

import io.reactivex.rxjava3.core.Observable;
import agent.interfaces.IAgentConfig;
import entities.interfaces.IEvent;
import eventhandler.interfaces.IGameEvents;
import gamestate.dto.GameStateGraph;
import gamestate.interfaces.IGameState;

import javax.inject.Inject;

public class AgentComponent implements IAgentConfig {
    private IGameState gameState;
    private IGameEvents eventHandler;

    private Observable<IEvent> gameEventStream;
    private Observable<GameStateGraph> gameStateStream;

    @Inject
    public AgentComponent(IGameState gameState, IGameEvents eventHandler) {
        this.gameState = gameState;
        this.eventHandler = eventHandler;

        this.gameStateStream = gameState.getGameStateStream();
        this.gameEventStream = eventHandler.getGameEventStream();
    }
}
