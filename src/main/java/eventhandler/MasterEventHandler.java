package eventhandler;

import eventhandler.interfaces.IEventHandler;
import messenger.interfaces.IMessenger;

public class MasterEventHandler implements IEventHandler {
    private IMessenger messenger;

    public MasterEventHandler(IMessenger messenger) {
        this.messenger = messenger;
    }
}
