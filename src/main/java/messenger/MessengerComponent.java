package messenger;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import entities.interfaces.IEvent;
import messenger.interfaces.IMessenger;
import network.dto.NetworkEvent;
import network.interfaces.INetwork;

import javax.inject.Inject;

public class MessengerComponent implements IMessenger {
    private INetwork network;

    private Observable<String> messageStream;
    private Observable<NetworkEvent> networkEventStream;
    private PublishSubject<IEvent> events = PublishSubject.create();

    @Inject
    public MessengerComponent(INetwork network) {
       this.network = network;
       this.messageStream = network.getMessageStream();
       this.networkEventStream = network.getNetworkEventStream();
    }

    @Override
    public Observable<IEvent> getGameEventStream() {
        return events.hide();
    }
}
