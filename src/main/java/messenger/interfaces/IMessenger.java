package messenger.interfaces;

import io.reactivex.rxjava3.core.Observable;
import entities.interfaces.IEvent;

import java.util.List;

public interface IMessenger {
    Observable<IEvent> getGameEventStream();
}
