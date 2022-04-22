package network;

import io.netty.channel.Channel;

import java.util.Scanner;

public class ScannerThread extends Thread{
    Channel channel;
    Scanner scanner;
    boolean active = true;

    ScannerThread(Channel channel){
        this.channel = channel;
        scanner = new Scanner(System.in);
    }

    public void setActive (Boolean bool){
        System.out.println("closing scanner");
        active = bool;
    }

    public void setChannel (Channel channel){
        this.channel = channel;
    }

    @Override
    public void run(){
        while (scanner.hasNextLine() && active) {
            String msg = scanner.nextLine();
            System.out.println("Input:" + msg);
            channel.writeAndFlush(msg);
            //channel.flush();
        }
    }


}
