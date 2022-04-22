package eventhandler;

import eventhandler.interfaces.IEventHandler;
import messenger.interfaces.IMessenger;

public class SlaveEventHandler implements IEventHandler {
    private IMessenger messenger;

    public SlaveEventHandler(IMessenger messenger) {
        this.messenger = messenger;
    }
}
