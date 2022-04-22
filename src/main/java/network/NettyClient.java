package network;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class NettyClient {
    private ScannerThread scanner = null;
    private Bootstrap b;
    private EventLoopGroup group;
    NettyClient client;
    //private ChannelFuture f;
    //private Channel channel;

    public NettyClient() {
        client = this;
    }

    public void setupClient() {
        group = new NioEventLoopGroup();
        b = new Bootstrap();
        b.group(group) // Set EventLoopGroup to handle all events for client.
                .channel(NioSocketChannel.class)// Use NIO to accept new connections.
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline p = ch.pipeline();
                        p.addLast(new StringDecoder());
                        p.addLast(new StringEncoder());
                        // This is our custom client handler which will have logic for chat.
                        p.addLast(new ClientHandler(client));

                    }
                });
    }

    public void runClient(String host, int port) throws InterruptedException {
        System.out.println("Starting up Client");
        ChannelFuture f = b.connect(host, port).sync();
        Channel channel = f.sync().channel();
        System.out.println("channel set");
        scanner = new ScannerThread(channel);
        System.out.println("scanner set");
        scanner.run();

        f.channel().closeFuture().sync();
    }

    public ScannerThread getScanner() {
        return scanner;
    }

    public void reconnectClient(String host, int port) throws InterruptedException {
        System.out.println("Reconnecting to server");
        runClient(host, port);
    }
}

class ClientHandler extends SimpleChannelInboundHandler<String> {

    private NettyClient client;
    static String BU_ip;
    static int BU_port;
    static String local_ip;

    public ClientHandler(NettyClient client){
        this.client = client;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println("Message from Server: " + msg);

        if (msg.contains("Second In Command: ")) {
            String split_one = msg.split("(?<=<<)")[1];
            String split_two = split_one.split("(?=>>)")[0];
            BU_ip = split_two.split(";")[0];
            BU_port = Integer.parseInt(split_two.split(";")[1]);
        }
        if (msg.contains("Your own IP: ")) {
            String split_one = msg.split("(?<=\\{)")[1];
            local_ip = split_one.split("(?=})")[0];
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (cause.getMessage() == "Connection reset") {
            System.err.println("Host disconnected. Terminating connection! Server: " + ctx);
            if (local_ip.equals(BU_ip)) {
                System.out.println("I am the backup host! Creating server...");
                client.getScanner().setActive(false);
                NettyServer server = new NettyServer();
                server.setupServer(BU_ip, 8007);
                server.runServerClient(client, BU_ip);
            } else {
                System.out.println("Connecting with host: " + BU_ip + ", port: " + BU_port + "... Please be patient. (not yet implemented)");
                client.getScanner().setActive(false);
                client.runClient(BU_ip, 8007);

            }
            ctx.close();
        } else {
            cause.printStackTrace();
        }
    }
}
