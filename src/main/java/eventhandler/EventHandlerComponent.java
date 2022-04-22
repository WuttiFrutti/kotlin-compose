package eventhandler;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import entities.interfaces.IEvent;
import eventhandler.interfaces.IEventHandler;
import eventhandler.interfaces.IGameEvents;
import messenger.interfaces.IMessenger;
import persistence.interfaces.IPersistence;

import javax.inject.Inject;

public class EventHandlerComponent implements IGameEvents {
    private IMessenger messenger;
    private IPersistence persistence;
    private IEventHandler eventHandler;

    private PublishSubject<IEvent> gameEventStream = PublishSubject.create();
    private Observable<IEvent> gameEventStreamMessenger;


    @Inject
    public EventHandlerComponent(IMessenger messenger, IPersistence persistence) {
        this.messenger = messenger;
        this.persistence = persistence;

        this.gameEventStreamMessenger = messenger.getGameEventStream();

        // Hier moet logica komen om te kiezen tussen master en slave handler
        this.eventHandler = new MasterEventHandler(messenger);
    }

    @Override
    public Observable<IEvent> getGameEventStream() {
        return Observable.merge(gameEventStream, gameEventStreamMessenger);
    }
}
