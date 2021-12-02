import java.io.*;
import java.net.*;

class TCPClient1 {

    public static void main(String[] args) throws IOException {
        String modifiedSentence;
        String userEqaution;

        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

        Socket clientSocket = new Socket("127.0.0.1", 6789);

        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());

        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        do {
            System.out.println(
                    "Enter the equation you want to send to the server or type Close Client to close the client:");
            userEqaution = inFromUser.readLine();
            System.out.println("sending your request to the server");
            outToServer.writeBytes(userEqaution + '\n');
            modifiedSentence = inFromServer.readLine();
            if (modifiedSentence.equals("close"))
                System.out.println("Exiting the client");
            else
                System.out.println("The server said that " + userEqaution + " = " + modifiedSentence);
        } while (!modifiedSentence.equals("close"));

        clientSocket.close();

    }

}
