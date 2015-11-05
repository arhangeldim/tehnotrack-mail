package ru.mail.track.reflection;

import java.lang.reflect.Field;

import ru.mail.track.protocol.pack.MessagePack;

/**
 *
 */
public class Main {


    public static void main(String[] args) throws IllegalAccessException, NoSuchFieldException {


        MessageBody body = new MessageBody();
        body.data = "hahah";
        body.timestamp = 100L;

        Message msg = new Message();
        msg.id = 12;
        msg.chatId = 1;
        msg.body = "Hahahah!";

        Class cls = msg.getClass();
        Field[] fields = cls.getDeclaredFields();

        MessagePack pack = new MessagePack(128);

        for (Field field : fields) {
            Class fCls = field.getType();
            pack.pack(fCls, field.get(msg));
        }


        Message msg2 = new Message();

        byte[] raw = pack.getData();
        MessagePack unpack = new MessagePack(raw);
        for (Field field : fields) {
            Class fCls = field.getType();
            System.out.println("getting: " + fCls.getName());
            field.set(msg2, unpack.unpack(fCls));
        }

        System.out.println(msg2.toString());


    }


}
