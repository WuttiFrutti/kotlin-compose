package network.interfaces;

import io.reactivex.rxjava3.core.Observable;
import network.dto.NetworkEvent;

public interface INetwork {
    Observable<String> getMessageStream();
    Observable<NetworkEvent> getNetworkEventStream();
}
