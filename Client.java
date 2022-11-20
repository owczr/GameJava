import java.io.*;
import java.net.*;
import java.util.Arrays;

public class Client {

  static DatagramSocket datagramSocket;

  public Client(int port) throws SocketException {
    datagramSocket = new DatagramSocket(port);
  }

  public static void run(int x, int y) throws IOException {
    System.out.println("Klient dziala");
// calling the constructor to create a datagram socket
//    DatagramSocket socket = new DatagramSocket();
    InetAddress address = InetAddress.getByName("10.10.10.108");

    int port = 5252;
    byte[] byteBuffer = {(byte)x, (byte)y};
    byte[] byteBuffer1 = new byte[2];
//    byte buf[] = new byte[2];
//    int coords[] = {x,y};
//    byte send[] = serialize(coords);
    DatagramPacket packet = new DatagramPacket(byteBuffer, 2, address, port);
    DatagramPacket packetReceive = new DatagramPacket(byteBuffer1, 2);
// calling send() method  
    datagramSocket.send(packet);
    System.out.println("The packets are sent successfully");

    //datagramSocket.receive(packetReceive);
    //System.out.println("The packet data received are: " + Arrays.toString(packetReceive.getData()));
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
