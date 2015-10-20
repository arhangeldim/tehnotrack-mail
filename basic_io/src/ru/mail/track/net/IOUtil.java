package ru.mail.track.net;

import java.io.Closeable;

/**
 *
 */
public class IOUtil {

    static void closeQuietly(Closeable res) {
        if (res != null) {
            try {
                res.close();
            } catch (Exception e) {
                // tsss!
            }
        }
    }
}
