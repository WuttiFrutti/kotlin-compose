package network;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import network.dto.NetworkEvent;
import network.interfaces.INetwork;

public class NetworkComponent implements INetwork {
    private PublishSubject<String> messageStream = PublishSubject.create();
    private PublishSubject<NetworkEvent> networkEventStream = PublishSubject.create();

    @Override
    public Observable<String> getMessageStream() {
        return messageStream.hide();
    }

    @Override
    public Observable<NetworkEvent> getNetworkEventStream() {
        return networkEventStream.hide();
    }
}