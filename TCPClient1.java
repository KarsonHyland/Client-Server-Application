import java.io.*;
import java.net.*;

class TCPClient1 {

    public static void main(String[] args) {
        String sentence;
        String modifiedSentence;
        String userEqaution;

        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

        Socket clientSocket = new Socket("127.0.0.1", 6789);

        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());

        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        sentence = inFromUser.readLine();

        outToServer.writeBytes(sentence + '\n');

        modifiedSentence = inFromServer.readLine();

        System.out.println("FROM SERVER: " + modifiedSentence);

        if (modifiedSentence.equals("OK")) {
            System.out.println(
                    "The client has successfully connected to the server! \n please send equations in the following format:\nnumber - number\nnumber + number\nnumber / number\nnumber * number");
        } else {
            System.out.println("connection to the server failed");
            return;
        }

        do {
            System.out.println("Enter the equation you want to send to the server:");
            userEqaution = inFromUser.readLine();
            System.out.println("sending your equation to the server");
            outToServer.writeBytes(userEqaution + '\n');
            modifiedSentence = inFromServer.readLine();
            System.out.println("The server said that " + userEqaution + " = " + modifiedSentence);
        } while (!modifiedSentence.equals("exit"));

        clientSocket.close();

    }

}
