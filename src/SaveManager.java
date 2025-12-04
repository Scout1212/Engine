import javax.swing.*;
import java.io.*;

public class SaveManager {
    public static Object load() {
        Object output = null;

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("." +"/LineSaves/"));
        int returnVal = fileChooser.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            try {
                InputStream inputStream = new FileInputStream(filePath);
                ObjectInputStream objectStream = new ObjectInputStream(inputStream);
                 output = objectStream.readObject();
                objectStream.close();
            } catch (Exception a) {
                a.printStackTrace();
            }
        }

        return output;
    }

    public static void save(String filePath, Object object) {
        if (filePath == null) {
            String fileName = JOptionPane.showInputDialog("What would you like to name your save file?") + ".obj";
            filePath = "LineSaves/" + fileName;
        }

        try {
            System.out.println("Saving to: " + filePath);
            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
            ObjectOutputStream objOutputStream = new ObjectOutputStream(fileOutputStream);
            objOutputStream.writeObject(object);
            objOutputStream.close();
        } catch (Exception a) {
            a.printStackTrace();
        }

        JOptionPane.showMessageDialog(null, "Saved Successfully!");
    }
}
