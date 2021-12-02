import java.io.*;
import java.net.*;

public class TCPServer {

    public static void main(String[] args) throws IOException {

        ServerSocket welcomeSocket = new ServerSocket(6789);

        // Socket connectionSocket = welcomeSocket.accept();
        while (true) {

            Socket connectionSocket = welcomeSocket.accept();

            System.out.println("new client is connected");

            ClientHandler clientSock = new ClientHandler(connectionSocket);

            new Thread(clientSock).start();

        }

    }

}

// ClientHandler class
class ClientHandler implements Runnable {
    private final Socket connectionSocket;

    // Constructor
    public ClientHandler(Socket socket) {
        this.connectionSocket = socket;
    }

    public void run() {
        DataOutputStream outToClient = null;
        BufferedReader in = null;
        int mathOpperation;
        int answer = 0;
        String clientSentence;
        String answerString;
        try {

            // get the outputstream of client
            outToClient = new DataOutputStream(connectionSocket.getOutputStream());

            // get the inputstream of client
            in = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));

            while ((clientSentence = in.readLine()) != null) {

                System.out.println("The eqaution sent was " + clientSentence);

                if (clientSentence.indexOf("+") != -1) {
                    mathOpperation = clientSentence.indexOf("+");
                    answer = Integer.parseInt(clientSentence.substring(0, mathOpperation))
                            + Integer.parseInt(clientSentence.substring(mathOpperation + 1));
                }

                else if (clientSentence.indexOf("-") != -1) {
                    mathOpperation = clientSentence.indexOf("-");
                    answer = Integer.parseInt(clientSentence.substring(0, mathOpperation))
                            - Integer.parseInt(clientSentence.substring(mathOpperation + 1));
                }

                else if (clientSentence.indexOf("/") != -1) {
                    mathOpperation = clientSentence.indexOf("/");
                    answer = Integer.parseInt(clientSentence.substring(0, mathOpperation))
                            / Integer.parseInt(clientSentence.substring(mathOpperation + 1));
                }

                else if (clientSentence.indexOf("*") != -1) {
                    mathOpperation = clientSentence.indexOf("*");
                    answer = Integer.parseInt(clientSentence.substring(0, mathOpperation))
                            * Integer.parseInt(clientSentence.substring(mathOpperation + 1));
                }

                if (clientSentence.equals("Close Client")) {
                    answerString = "close";
                } else {
                    System.out.println("The equation sent was " + clientSentence + " and the answer is " + answer);
                    answerString = String.valueOf(answer);
                }

                outToClient.writeBytes(answerString + '\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (outToClient != null) {
                    outToClient.close();
                }
                if (in != null) {
                    in.close();
                    connectionSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
