import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Arrays;

// Odbrac pakiety
// Zmodyfikowac stan gry
// Odeslac stan gry
// setOnKeyPressed
// DatagramSocket
// DatagramPacket

public class Server {
    // connect
    // sending
    // receiving
    static DatagramSocket datagramSocket;
    public Server(int port) throws SocketException {
        datagramSocket = new DatagramSocket(port);
    }

    public static void run(int x, int y) throws IOException, ClassNotFoundException {
        // TODO: Implement receiving and sending game objects
        // Sketch:
        //System.out.println("Server send x " + x + " y " + y);
        byte buf[] = new byte[2];
        byte send[] = {13, 18}; // Game()
        // int coords[] = {x, y};
        // byte send[] = serialize(coords);
        DatagramPacket datagramPacket = new DatagramPacket(buf, 2);
//        DatagramPacket sendpacket = new DatagramPacket(send, 2,
//                InetAddress.getByName("10.10.10.100"), 5252);
//        datagramSocket.send(sendpacket);
//        System.out.println("Server send");
        datagramSocket.receive(datagramPacket);
        System.out.println(Arrays.toString(datagramPacket.getData()));
        DatagramPacket sendpacket = new DatagramPacket(send, 2,
                datagramPacket.getAddress(), datagramPacket.getPort());
        System.out.println("Server send");
        datagramSocket.send(sendpacket);
    }

    public void connect() {
        // TODO: Implement connection method
    }

    public void send() {
        // TODO: Implement sending method
    }

    public void receive() {
        // TODO: Implement receiving method
    }

    // used to pack game object
    public static byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(out);
        os.writeObject(obj);
        return out.toByteArray();
    }

    // used to unpack received object
    public static Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
        // FIXME: Zawiesza sie przy wywolaniu
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream is = new ObjectInputStream(in);
        return is.readObject();
    }
}
