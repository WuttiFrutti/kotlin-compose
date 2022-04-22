package eventhandler.interfaces;

import io.reactivex.rxjava3.core.Observable;
import entities.interfaces.IEvent;

public interface IGameEvents {
    Observable<IEvent> getGameEventStream();
}


