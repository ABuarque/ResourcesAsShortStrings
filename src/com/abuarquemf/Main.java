package com.abuarquemf;

import java.io.*;
import java.nio.file.Files;
import java.util.Base64;


/**
 * Utils for (de)serialization into Base64-encoded string for future persisting.
 *
 * @author andy (https://gist.github.com/andy722/1524968)
 */
class SerializationUtils {

    public static <T extends Serializable> String serialize(T item) {
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        final ObjectOutputStream objectOutputStream;
        try {
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(item);
            objectOutputStream.close();
            return Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            throw new Error(e);
        }
    }

    public static <T extends Serializable> T deserialize(String data) {
        try {
            byte[] dataBytes = Base64.getDecoder().decode(data);
            final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(dataBytes);
            final ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);

            @SuppressWarnings({"unchecked"})
            final T obj = (T) objectInputStream.readObject();

            objectInputStream.close();
            return obj;
        } catch (IOException e) {
            throw new Error(e);
        } catch (ClassNotFoundException e) {
            throw new Error(e);
        }
    }
}

public class Main {

    public static String path = "/home/animal505/IdeaProjects/ByteArrays/src/com/abuarquemf/ct.mp3";

    public static void createResourceFile(byte[] resourceAsBytes, String resourceName) {
        try (FileOutputStream fos = new FileOutputStream(resourceName)) {
            fos.write(resourceAsBytes);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        //generated string is smaller
        String key = SerializationUtils.serialize(new File(path));
        System.out.println(key);

        PrintWriter p = new PrintWriter("/home/animal505/IdeaProjects/ByteArrays/src/com/abuarquemf/ai.txt");
        p.write(key);
        p.close();

        File x = SerializationUtils.deserialize(key);
        createResourceFile(Files.readAllBytes(x.toPath()),
               "/home/animal505/IdeaProjects/ByteArrays/src/com/abuarquemf/eae");

    }

}
