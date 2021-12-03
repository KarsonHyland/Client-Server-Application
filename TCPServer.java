import java.io.*;
import java.net.*;

// TCPServer Class that extends createfile1 in order to create an object of it and use it's commands
public class TCPServer extends createfile1 {

    //Main function
    public static void main(String[] args) throws IOException {

        // Initialize a ServerSocket and createfile1 objects
        ServerSocket welcomeSocket = new ServerSocket(6789);
        createfile1 mylog = new createfile1("logg.txt");

        // Socket connectionSocket = welcomeSocket.accept();
        while (true) {

            //open connection is created
            Socket connectionSocket = welcomeSocket.accept();
            mylog.logInfo("New client has been connected");

            // Print out statement to present that a connection is successful
            System.out.println("new client is connected");

            ClientHandler clientSock = new ClientHandler(connectionSocket);

            //Allows for multiple client connections to be made
            new Thread(clientSock).start();

        }

    }

}

// ClientHandler class
class ClientHandler implements Runnable {
    private final Socket connectionSocket;
    private createfile1 myLog = new createfile1("logg.txt");

    // Constructor
    public ClientHandler(Socket socket) {
        this.connectionSocket = socket;
    }

    // The main function for doing basic calculations
    public void run() {

        //Initialize variables
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

            // Reads the input string and determines the operation that must take place in the equation
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

                // If "Close Client" string is inputted the connection with the client is closed and no more input is taken
                if (clientSentence.equals("Close Client")) {
                    answerString = "close";
                    this.myLog.logInfo("Client has been closed");
                } else {
                    System.out
                            .println("The equation sent was " + clientSentence + " and the answer is " + answer
                                    + " ");
                    answerString = String.valueOf(answer);
                    this.myLog.logInfo("Client sent " + clientSentence + " which equals " + answer + " ");
                }

                outToClient.writeBytes(answerString + '\n');
            }
        } catch (IOException e) {
            //Error handling
            e.printStackTrace();
        } finally {
            // Clean up code/ Last case error handling
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