import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

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

    public static void run(String[] args) throws IOException {
        // TODO: Implement receiving and sending game objects
        // Sketch:
        DatagramSocket datagramSocket = new DatagramSocket(5252);
        byte buf[] = new byte[2];
        byte send[] = {13, 18}; // Game()
        DatagramPacket datagramPacket = new DatagramPacket(buf, 2);
        datagramSocket.receive(datagramPacket);
        System.out.println("aaaaaaaaa");
        System.out.println(datagramPacket.getData());
        DatagramPacket sendpacket = new DatagramPacket(send, 2,
                datagramPacket.getAddress(), datagramPacket.getPort());
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
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream is = new ObjectInputStream(in);
        return is.readObject();
    }
}
