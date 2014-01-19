package net.nokok.twitduke.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * 渡されたオブジェクトのディープコピーなどの機能を提供します
 */
public class ObjectCloner {

    public static Object cloneObject(Serializable object) {
        if (object == null) {
            throw new InternalError();
        }
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
             ByteArrayInputStream inputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
             ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)) {
            objectOutputStream.writeObject(object);
            return objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new InternalError(e.getMessage());
        }
    }
}
