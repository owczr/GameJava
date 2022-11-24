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

  public static void run(gameState gs) throws IOException, ClassNotFoundException {
    InetAddress address = InetAddress.getByName("10.10.10.108");
    byte send[] = serialize(gs);
    byte buf[] = new byte[send.length];
    //byte[] byteBuffer = {(byte)x, (byte)y};
    //byte[] byteBuffer1 = new byte[2];

//    System.out.println(datagramSocket.getLocalPort());
    DatagramPacket packet = new DatagramPacket(send, send.length, address, datagramSocket.getLocalPort() );
    DatagramPacket packetReceive = new DatagramPacket(buf, buf.length);

    datagramSocket.send(packet);
    System.out.println("Client send");

    System.out.println("Sent Client: ");
    System.out.println("X: "+ gs.p_c.x+" Y= "+gs.p_c.y);
    System.out.println("Sent Server: ");
    System.out.println("X: "+ gs.p_s.x+" Y= "+gs.p_s.y);

    datagramSocket.receive(packetReceive);
    System.out.println("Client received");
    //System.out.println("The packet data received are: " + Arrays.toString(packetReceive.getData()));
//    System.out.println("The packet data received are: " + Arrays.toString(new Object[]{deserialize(packetReceive.getData())});
    gameState rec_data = deserialize(packetReceive.getData());
    System.out.println("Client: ");
    System.out.println("X: "+ rec_data.p_c.x+" Y= "+rec_data.p_c.y);
    System.out.println("Server: ");
    System.out.println("X: "+ rec_data.p_s.x+" Y= "+rec_data.p_s.y);

    gs.p_s = rec_data.p_s;
  }

  // used to pack game object
  public static byte[] serialize(gameState obj) throws IOException {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    ObjectOutputStream os = new ObjectOutputStream(out);
    os.writeObject(obj);
    return out.toByteArray();
  }

  // used to unpack received object
  public static gameState deserialize(byte[] data) throws IOException, ClassNotFoundException {
    ByteArrayInputStream in = new ByteArrayInputStream(data);
    ObjectInputStream is = new ObjectInputStream(in);
    return (gameState)is.readObject();
  }
}
