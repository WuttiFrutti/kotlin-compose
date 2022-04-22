package gamestate.interfaces;

import io.reactivex.rxjava3.core.Observable;
import gamestate.dto.GameStateGraph;

public interface IGameState {
    Observable<GameStateGraph> getGameStateStream();
//    void sendEvents(List<IEvent> events);
}
