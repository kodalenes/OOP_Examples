import java.io.*;

public class FileManager {

    public static void saveToFile(Object object , String filePath) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(filePath);
        ObjectOutputStream objectOutputStream =new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(object);
    }

    public static Object loadFromFile(String filePath) throws IOException , ClassNotFoundException
    {
        FileInputStream fileInputStream = new FileInputStream(filePath);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        return objectInputStream.readObject();
    }
}
