package gamestate;

import io.reactivex.rxjava3.core.Observable;
import entities.interfaces.IEvent;
import eventhandler.interfaces.IGameEvents;
import gamestate.dto.GameStateGraph;
import gamestate.interfaces.IGameState;

import javax.inject.Inject;

public class GameStateComponent implements IGameState {
    private IGameEvents eventHandler;

    private Observable<IEvent> gameEventStream;

    @Inject
    public GameStateComponent(IGameEvents eventHandler) {
        this.eventHandler = eventHandler;
        this.gameEventStream = eventHandler.getGameEventStream();
    }

    @Override
    public Observable<GameStateGraph> getGameStateStream() {
        return gameEventStream.map(ignored -> new GameStateGraph());
    }
}
