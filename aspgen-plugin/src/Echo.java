import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Echo {
    public static void main(final String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        final ServerSocket serverSocket = new ServerSocket(9255);
        final Socket connection = serverSocket.accept();

        final String line;
        BufferedReader in = null;
        PrintWriter out = null;
        try{
            in = new BufferedReader(new
              InputStreamReader(connection.getInputStream()));
            out = new
              PrintWriter(connection.getOutputStream(), true);
          } catch (final IOException e) {
            System.out.println("in or out failed");
            System.exit(-1);
          }
        while (true) {
            Thread.sleep(10000);
            System.out.println("refresh");
            out.println("D:/Furi/workspace-sts-2.9.2.RELEASE/Playground/test");
        }
//        System.out.println("finish");
    }
}