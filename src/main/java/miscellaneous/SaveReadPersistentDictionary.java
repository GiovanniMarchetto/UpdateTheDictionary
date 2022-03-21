package miscellaneous;

import dataStructure.Dictionary;

import java.io.*;

public class SaveReadPersistentDictionary implements Serializable
{
    private static final String PATH = System.getProperty("user.dir") + "\\src\\main\\resources\\persistentDictionary";

    public void saveFile(Dictionary dictionary)
            throws IOException
    {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(PATH))) {
            outputStream.writeObject(dictionary);
        }
    }

    public Dictionary readFile()
            throws ClassNotFoundException, IOException
    {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(PATH))) {
            return (Dictionary) inputStream.readObject();
        }
    }

}