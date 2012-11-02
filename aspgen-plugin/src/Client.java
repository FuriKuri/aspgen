import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class Client {
    public static void main(final String[] args) throws IOException, ClassNotFoundException {
        final Socket connection = new Socket("localhost", 9876);

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
        out.println("hello");
    }
}
