package network;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class NettyServer {
    // Port where chat server will listen for connections.
    private int PORT;
    private String IP;
    private int bh_counter = 1;
    private String BU_ip;
    private int BU_port = 0;
    static final List<Channel> channels = new ArrayList<>();
    private NettyServer server;
    private ServerBootstrap b;
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

    public NettyServer() {
        server = this;
    }

    public void setupServer(String ip, int port) {
        System.out.println("Setting up server on " + ip + ":" + port);
        PORT = port;
        IP = ip;
        bossGroup = new NioEventLoopGroup(1);
        workerGroup = new NioEventLoopGroup();
        b = new ServerBootstrap();
        b.group(bossGroup, workerGroup) // Set boss & worker groups
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline p = ch.pipeline();
                        p.addLast(new StringDecoder());
                        p.addLast(new StringEncoder());
                        p.addLast(new ServerHandler(server));
                    }
                });
        System.out.println("Server has been set up and is ready to run");
    }

    public void runServer() throws InterruptedException{
        try {
            System.out.println("Starting up Server on port: " + PORT);
            // Start the server.
            ChannelFuture f = b.bind(PORT).sync();
            System.out.println("Netty Server started.");
            // Wait until the server socket is closed.
            f.channel().closeFuture().sync();
        } finally {
            // Shut down all event loops to terminate all threads.
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public void runServerClient(NettyClient client, String bu_ip) throws InterruptedException{
        try {
            System.out.println("Starting up Server and Client.");
            System.out.println("Starting up Server on port: " + PORT);
            //start the server.
            ChannelFuture f = b.bind(PORT).sync();
            System.out.println("Netty Server started.");
            client.runClient(bu_ip, PORT);
            // Wait until the server socket is closed.
            f.channel().closeFuture().sync();
        } finally {
            // Shut down all event loops to terminate all threads.
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public String getConnectionDetails(int IPorPort) {
        return switch (IPorPort) {
            case 1 -> IP;
            case 2 -> Integer.toString(PORT);
            default -> null;
        };
    }

    //Getters & Setters
    public int getBh_counter() {
        return bh_counter;
    }
    public void setBh_counter(int bh_counter) {
        this.bh_counter = bh_counter;
    }
    public String getBU_ip() {
        return BU_ip;
    }
    public void setBU_ip(String BU_ip) {
        this.BU_ip = BU_ip;
    }
    public int getBU_port() {
        return BU_port;
    }
    public void setBU_port(int BU_port) {
        this.BU_port = BU_port;
    }
    public void addToChannel(ChannelHandlerContext ctx) {
        channels.add(ctx.channel());
    }
    public List<Channel> getChannels() {
        return channels;
    }
    public Channel getFromChannel(ChannelHandlerContext ctx) {
        return channels.stream().filter(c -> c == ctx.channel()).findFirst().orElse(null);
    }
}

@Sharable
class ServerHandler extends SimpleChannelInboundHandler<String> {
    private final NettyServer server;

    public ServerHandler(NettyServer server) {
        this.server = server;
    }

    @Override
    public void channelActive(final ChannelHandlerContext ctx) {
        System.out.println("Client joined - " + ctx);
        server.addToChannel(ctx);

        String local_ip = ((InetSocketAddress) ctx.channel().remoteAddress()).getAddress().getHostAddress();

        System.out.println(server.getChannels().size());
        System.out.println(server.getBh_counter());
        if (server.getChannels().size() == server.getBh_counter()) {
            if (!local_ip.equals(server.getConnectionDetails(1))) {
                ctx.writeAndFlush("You are now backup host. GZ!\n");
                server.setBU_ip(((InetSocketAddress) ctx.channel().remoteAddress()).getAddress().getHostAddress());
                server.setBU_port(((InetSocketAddress) ctx.channel().remoteAddress()).getPort());
            } else {
                server.setBh_counter(server.getBh_counter() + 1);
                server.setBU_ip("Not yet set. Client cannot be host, wait for additional player!");
            }
        }

        ctx.writeAndFlush("Mornin'! Welcome to the Server. Let me provide you with some details about my server :-)\n" +
                "Information about myself: " + ctx + ".\n" +
                "Information about my Second In Command: <<" + server.getBU_ip() + ";" + server.getBU_port() + ">>\n" +
                "Your own IP: {" + local_ip + "}\n" +
                "Chocolate cake gummies gingerbread icing tiramisu!\n");

    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, String msg) {
        System.out.println("Notification! Details: \n{\nSender: " + ctx
                + ".\nReceived: " + msg + "\n}\n");
        server.getFromChannel(ctx).writeAndFlush("Your notification is received!");
        for (Channel c : server.getChannels()) {
            c.writeAndFlush("Hello. A notification is received! Server playback:\n " + msg + "\n" + ctx + "\n");
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        System.out.println("Closing connection for client - " + ctx);
        ctx.close();
        server.getChannels().removeIf(c -> ctx.channel() == c);
    }
}
