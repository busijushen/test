import java.io.IOException;
 import java.net.InetSocketAddress;
 import java.nio.ByteBuffer;
 import java.nio.channels.ServerSocketChannel;
 import java.nio.channels.SocketChannel;
 import java.nio.charset.Charset;
  
public class ChannelEchoServer {
 
    public static final int ECHO_PORT = 10007;
 
    public static void main(String[] args) {
        new ChannelEchoServer().run();
    }
     
    public void run() {
        ServerSocketChannel serverChannel = null;
        try {
            serverChannel = ServerSocketChannel.open();
            serverChannel.socket().bind(new InetSocketAddress(ECHO_PORT));
            System.out.println("ChannelEchoServerが起動しました(port="
                    + serverChannel.socket().getLocalPort() + ")");
            while (true) {
                SocketChannel channel = serverChannel.accept();
                System.out.println(channel.socket().getRemoteSocketAddress() + ":[接続されました]");
                ChannelEchoThread cet = new ChannelEchoThread(channel);
                cet.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverChannel != null && serverChannel.isOpen()) {
                try {
                    System.out.println("ChannelEchoServerを停止します。");
                    serverChannel.close();
                } catch (IOException e) {}
            }
        }
    }
 
}
 
class ChannelEchoThread extends Thread {
 
    private static final int BUF_SIZE = 1000;
    SocketChannel channel = null;
 
    public ChannelEchoThread(SocketChannel channel) {
        this.channel = channel;
    }
 
    public void run() {
        ByteBuffer buf = ByteBuffer.allocate(BUF_SIZE);
        Charset charset = Charset.forName("UTF-8");
        String remoteAddress = channel.socket()
                .getRemoteSocketAddress()
                .toString();
        try {
        	System.out.println("1.buf limit:" + buf.limit() + ";position:" + buf.position() + ";capacity:" + buf.capacity());
            if (channel.read(buf) < 0) {
                return;
            }
            System.out.println("2.buf limit:" + buf.limit() + ";position:" + buf.position() + ";capacity:" + buf.capacity());
            buf.flip();
            System.out.println("3.buf limit:" + buf.limit() + ";position:" + buf.position() + ";capacity:" + buf.capacity());
            String input = charset.decode(buf).toString();
            System.out.print(remoteAddress + ":" + input);
            buf.flip();
            System.out.println("4.buf limit:" + buf.limit() + ";position:" + buf.position() + ";capacity:" + buf.capacity());
            channel.write(buf);
            System.out.println("5.buf limit:" + buf.limit() + ";position:" + buf.position() + ";capacity:" + buf.capacity());
        } catch (IOException e) {
            e.printStackTrace();
            return;
        } finally {
            System.out.println(remoteAddress + ":[切断しました]");
            if (channel != null && channel.isOpen()) {
                try {
                    channel.close();
                } catch (IOException e) {}
            }
        }
    }
 
}