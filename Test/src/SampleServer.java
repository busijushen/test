import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
 
public class SampleServer {
 
    private static final int SERVER_PORT = 1234;
    private static final int SERVER_TIMEOUT = 100000;
    private static final int SOCKET_TIMEOUT = 100000;
    private static final int BUF_SIZE = 4096;
    private static final String TERMINATER = "OVER!";
 
    // ノンブロッキングI/Oを用いたソケットサーバ実装サンプル
    public static void main(String[] args) {
        SampleServer sample = new SampleServer();
        sample.execute();
    }
 
    // メイン処理
    private void execute() {
        try (ServerSocketChannel server = ServerSocketChannel.open();) {
 
            server.configureBlocking(false); // ノンブロッキングモードに設定
            server.bind(new InetSocketAddress(SERVER_PORT));
            server.socket().setSoTimeout(SERVER_TIMEOUT);
 
            // ノンブロッキング処理のためのイベントセレクタ
            Selector selector = Selector.open();
 
            // セレクタにポート待ちうけ処理を追加
            server.register(selector, SelectionKey.OP_ACCEPT);
 
            while (true) {
                // 次に処理できるイベントを取得(ここはイベント発生までブロック)
                selector.select();
 
                for (SelectionKey key : selector.selectedKeys()) {
                    if (key.isAcceptable()) {
                        accept(server, selector);
 
                    } else if (key.isReadable()) {
                        read(key);
                    }
 
                }
            }
 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
    // 待ちうけ処理
    private void accept(ServerSocketChannel server, Selector selector)
            throws IOException {
        SocketChannel stream = server.accept();
 
        if (stream != null) {
            // ノンブロッキングモードに設定
            stream.configureBlocking(false);
 
            // セレクタに受信処理を登録
            stream.register(selector, SelectionKey.OP_READ);
 
        }
 
    }
 
    // 受信処理
    private void read(SelectionKey key) throws IOException {
        SocketChannel stream = null;
 
        try {
            stream = (SocketChannel) key.channel();
            stream.socket().setSoTimeout(SOCKET_TIMEOUT);
 
            // パケット読み込み用バッファ
            ByteBuffer bb = ByteBuffer.allocate(BUF_SIZE);
            Charset charset = Charset.forName("UTF-8");
 
            // 受信データをバッファに読み込み
            int ret = stream.read(bb);
 
            // データが未着(=0)、もしくは通信切断(=-1)の場合、読み込み終了
            if (ret == 0) {
 
            } else if (ret == -1) {
                stream.close();
 
            } else {
                bb.flip();
                String str = charset.decode(bb).toString();
                System.out.println(str);
 
                // 終端文字列が送られてきた場合、ACKを返却
                if (str.lastIndexOf(TERMINATER) != -1) {
                    // ACKを送信
                    // (送信はもともとノンブロッキングなのでselecterは使わない)
                    stream.write(ByteBuffer
                            .wrap(("ACK\r\n" + TERMINATER + "\r\n")
                                    .getBytes()));
 
                }
 
            }
 
        } catch (IOException e) {
            stream.close();
            throw e;
        }
    }
 
}