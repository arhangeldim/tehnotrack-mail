package ru.mail.track.protocol.pack;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

import static org.junit.Assert.*;

/**
 *
 */
public class MessagePackTest {

    MessagePack pack;

    @After
    public void tearDown() throws Exception {

    }

    @Before
    public void setUp() {
        pack = new MessagePack(64);
    }

    @Test
    public void testPutByte() throws Exception {

    }

    @Test
    public void testPutInt() throws Exception {
//        pack.packInt(Integer.MAX_VALUE);
//        pack.reset();
//
//        assertEquals(Integer.MAX_VALUE, pack.unpackInt());


        int[] data = {Integer.MIN_VALUE, Integer.MAX_VALUE, 0, 128, -1};
        for (int it : data) {
            pack.packInt(it);
        }
        pack.reset();
        for (int i = 0; i < data.length; i++) {
            assertEquals(data[i], pack.unpackInt());
        }
    }

    @Test
    public void packUnpackStr() throws Exception {

        List<String> str = Arrays.asList("A", "TEST", "foo", "hello", "");
        str.forEach(pack::packStr);
        pack.reset();
        for (int i = 0; i < str.size(); i++) {
            assertEquals(str.get(i), pack.unpackStr());
        }
    }

}
