import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;

/**
 * ノンブロッキング IO サーバのサンプルソース
 * 
 * 
 * @author 22899113
 *
 */
public class NonBlockingServer {

    private static final int SERVER_PORT = 8888;
    private static final int BUF_SIZE = 2000;

    private Selector selector;

    public static void main(String[] args) {
        NonBlockingServer nserver = new NonBlockingServer();
        nserver.start();
    }


    public void start(){
        ServerSocketChannel serverChannel = null;
        try {
            selector = Selector.open();
            serverChannel = ServerSocketChannel.open();
            serverChannel.configureBlocking(false);
            serverChannel.socket().bind(new InetSocketAddress(SERVER_PORT));
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);
            while (selector.select() > 0) {
                for (Iterator<SelectionKey> it = selector.selectedKeys().iterator();
                    it.hasNext();) {
                    SelectionKey key = it.next();
                    it.remove();
                    if (key.isAcceptable()) {
                        doAccept((ServerSocketChannel) key.channel());
                    } else if (key.isReadable()) {
                        SocketChannel channel = (SocketChannel)key.channel();
                        doRead(channel);
                    }
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private void doAccept(ServerSocketChannel serverChannel) {
    	System.out.println("doAccept start");
        try {
            SocketChannel channel = serverChannel.accept();
            channel.configureBlocking(false);
            channel.register(selector, SelectionKey.OP_READ);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        System.out.println("doAccept end");
    }

    private void doRead(SocketChannel channel) {
    	System.out.println("doRead start");
        ByteBuffer buf = ByteBuffer.allocate(BUF_SIZE);
        Charset charset = Charset.forName("UTF-8");
        try {
            if (channel.read(buf) < 0) {
                return;
            }
            buf.flip();
            System.out.print(
                    charset.decode(buf).toString());
            buf.flip();
            channel.write(buf);
        } catch (IOException ioe) {
           ioe.printStackTrace();
        }
    	System.out.println("doRead end");
    }
}