import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Arrays;

public class Client {

  static DatagramSocket datagramSocket;

  public Client(int port) throws SocketException {
    datagramSocket = new DatagramSocket(port);
  }

  public static void run(P_move p) throws IOException, ClassNotFoundException {
    InetAddress address = InetAddress.getByName("10.10.10.108");
    byte buf[] = new byte[43];
    //byte[] byteBuffer = {(byte)x, (byte)y};
    //byte[] byteBuffer1 = new byte[2];
    byte send[] = serialize(p);
    DatagramPacket packet = new DatagramPacket(send, send.length, address, datagramSocket.getPort());
    DatagramPacket packetReceive = new DatagramPacket(buf, 43);

    datagramSocket.send(packet);
    System.out.println("Client send");

    datagramSocket.receive(packetReceive);
    System.out.println("Client received");
    //System.out.println("The packet data received are: " + Arrays.toString(packetReceive.getData()));
  }

  // used to pack game object
  public static byte[] serialize(P_move obj) throws IOException {
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