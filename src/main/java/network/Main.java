package network;

import java.io.IOException;

public class Main {
    //empty
}

class NettyServerMain {
    public static void main (String[] args) throws Exception {
        NettyServer server = new NettyServer();
        server.setupServer("192.168.154.187", 8007);
        server.runServer();
    }
}

class NettyClientMain {
    public static void main (String[] args) throws Exception {
        NettyClient client = new NettyClient();
        client.setupClient();
        client.runClient("192.168.154.187", 8007);
    }
}

