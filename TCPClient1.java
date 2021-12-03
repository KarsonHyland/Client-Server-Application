import java.io.*;
import java.net.*;

class TCPClient1 {

    public static void main(String[] args) throws IOException {
        //these strings are going to be used to take in the answers from the server and the input for the user
        String modifiedSentence;
        String userEqaution;

        //up until the do while loop the code just connects the client to the server and sets up the input and output streams
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

        //this connects the client to the server
        Socket clientSocket = new Socket("127.0.0.1", 6789);

        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());

        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        //this will loop through asking the user to input equations until the input "Close client" which will close the client.
        do {
            System.out.println(
                    "Enter the equation you want to send to the server or type Close Client to close the client:");
            //takes in user input
            userEqaution = inFromUser.readLine();
            System.out.println("sending your request to the server");
            //sends the equation to the server
            outToServer.writeBytes(userEqaution + '\n');
            //gets the answer from the server
            modifiedSentence = inFromServer.readLine();
            //tests if the client is closing if not print the equation with answer
            if (modifiedSentence.equals("close"))
                System.out.println("Exiting the client");
            else
                System.out.println("The server said that " + userEqaution + " = " + modifiedSentence);
        } while (!modifiedSentence.equals("close"));

        //close the socket
        clientSocket.close();

    }

}
