import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter; // Import the FileWriter class
import java.io.IOException; // Import the IOException class to handle errors
import java.text.SimpleDateFormat;
import java.util.Date;

public class createfile1 {
    private FileWriter myWriter;
    private File f1;
    private BufferedWriter bw;

    public createfile1() {

    }

    public createfile1(String file_name) {
        try {
            this.f1 = new File(file_name);
            if (!f1.exists()) {
                this.f1.createNewFile();
            }
            this.myWriter = new FileWriter(f1);
            this.bw = new BufferedWriter(myWriter);
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void logInfo(String stuff) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();
            this.bw.write(formatter.format(date) + "\n" + stuff);
            bw.flush();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void closer() {
        try {
            this.bw.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}