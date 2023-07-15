package Olga;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel ssc = ServerSocketChannel.open(); // открываем канал
        ssc.bind(new InetSocketAddress(40001)); // приделываем канал к порту
        ssc.configureBlocking(false); // делаем канал неблокирующим
        Selector selector = Selector.open(); // открываем работу селектора
        ssc.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("Selector server is ready");
        while(true) {
            if (selector.select() == 0) { // если селектор не получил никаких соединений -
                continue; // - переходим к следующему шагу итерации
            }
            Set<SelectionKey> keys = selector.selectedKeys(); // получаем сет с ключами
            Iterator<SelectionKey> iterator = keys.iterator(); // вытаскиваем итератор
            while(iterator.hasNext()) {
                SelectionKey key = iterator.next(); // вытаскиваем ключ из множества ключей
                if (key.isAcceptable()) { // проверяем, что тип соединения "соединяющий"
                    SocketChannel s = ssc.accept(); // получаем сокетный канал
                    System.out.println("Client connected: " + s);
                    s.configureBlocking(false); // делаем канал неблокирующим
                    s.register(selector, SelectionKey.OP_READ); // регистрируем канал как канал для чтения
                }
                if (key.isReadable()) { // проверяем, что тип соединения "читающий"
                    SocketChannel s = (SocketChannel) key.channel(); // получаем сокетный канал самого ключа
                    ByteBuffer buffer = ByteBuffer.allocate(1024); // создаём буфер
                    int readResult = s.read(buffer);
                    if (readResult > 0) {// если результат чтения больше ноля (ноль - читать нечего, -1 - мы достигли конца)
                        String messageFromClient = new String(buffer.array(), 0, readResult, StandardCharsets.UTF_8); // считываем содержимое буфера
                        System.out.println("Info from client: " + messageFromClient);
                        String messageToClientFromDragons = "Info wrote";
                        s.write(buffer.wrap(messageToClientFromDragons.getBytes(StandardCharsets.UTF_8)));
                        s.close();
                    }
                }
                iterator.remove();
            }
        }
    }
}
