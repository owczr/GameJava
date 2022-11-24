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

    public void run(gameState g_s) throws IOException, ClassNotFoundException {
        // TODO: Implement receiving and sending game objects
        // byte send[] = {(byte)x, (byte)y}; // Game()
        byte send[] = serialize(g_s);
        byte buf[] = new byte[send.length];

        DatagramPacket datagramPacket = new DatagramPacket(buf, buf.length); // do odbierania inny buf i length

        datagramSocket.receive(datagramPacket);

        gameState rec_data = deserialize(datagramPacket.getData());
        System.out.println("Received client player  X: " + rec_data.p_c.x + "Y: " + rec_data.p_c.y);
        System.out.println("Received server player  X: " + rec_data.p_s.x + "Y: " + rec_data.p_s.y);

        g_s.p_c = rec_data.p_c;
        send = serialize(g_s);

        DatagramPacket sendpacket = new DatagramPacket(send, send.length,
               datagramPacket.getAddress(), datagramPacket.getPort());

        System.out.println("Sent client player      X: " + g_s.p_c.x + "Y: " + g_s.p_c.y);
        System.out.println("Sent server player      X: " + g_s.p_s.x + "Y: " + g_s.p_s.y);

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
    public static byte[] serialize(gameState obj) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream(6400);
        ObjectOutputStream os = new ObjectOutputStream(out);
        os.writeObject(obj);
        return out.toByteArray();
    }

    // used to unpack received object
    public static gameState deserialize(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream is = new ObjectInputStream(in);
        return (gameState) is.readObject();
    }
}
