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

    public static void run(P_move p) throws IOException, ClassNotFoundException {
        // TODO: Implement receiving and sending game objects
        byte buf[] = new byte[2];
        // byte send[] = {(byte)x, (byte)y}; // Game()
        byte send[] = serialize(p);
        DatagramPacket datagramPacket = new DatagramPacket(buf, 2); // do odbierania inny buf i length
        datagramSocket.receive(datagramPacket);
        System.out.println(Arrays.toString(datagramPacket.getData()));
        DatagramPacket sendpacket = new DatagramPacket(send, send.length,
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
    public static byte[] serialize(P_move obj) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream(6400);
        ObjectOutputStream os = new ObjectOutputStream(out);
        os.writeObject(obj);
        return out.toByteArray();
    }

    // used to unpack received object
    public static P_move deserialize(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream is = new ObjectInputStream(in);
        return (P_move)is.readObject();
    }
}
