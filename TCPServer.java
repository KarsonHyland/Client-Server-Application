import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;  
import java.util.Date; 
import java.nio.file.Files;

public class TCPServer {

    public static void main(String[] args) throws IOException {

        ServerSocket welcomeSocket = new ServerSocket(6789);
        logg myLog = new logg("logg.txt");

        // Socket connectionSocket = welcomeSocket.accept();
        while (true) {

            Socket connectionSocket = welcomeSocket.accept();
            mylog.logInfo("New client has been connected")

            System.out.println("new client is connected");

            ClientHandler clientSock = new ClientHandler(connectionSocket);

            new Thread(clientSock).start();

        }

    }

}

// ClientHandler class
class ClientHandler implements Runnable {
    private final Socket connectionSocket;
    logg myLog = new logg("logg.txt");

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
                    this.mylog.logInfo("Client has been closed")
                } else {
                    System.out.println("The equation sent was " + clientSentence + " and the answer is " + answer);
                    answerString = String.valueOf(answer);
                    this.mylog.logInfo("Client sent " + clientSentence + " which equals " + answer);
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

public class logg {
    FileWriter writer;
    filehandler fn;

    public logg(String file_name) throws SecurityException, IOException{
        this.writer = new FileWriter(file_name); 
    }

    public logInfo(String info){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
        Date date = new Date();  
        writer.write(formatter.format(date) + "\n" + info); 
    }
}
