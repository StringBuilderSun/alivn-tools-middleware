package com.kafka.service.serializer;

import kafka.serializer.Decoder;
import kafka.serializer.Encoder;
import kafka.utils.VerifiableProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * Created by lijinpeng on 2018/8/8.
 */
public class MessageSerializer<V> implements Encoder<V>, Decoder<V> {
    private static final Logger log = LoggerFactory.getLogger(MessageSerializer.class);

    public MessageSerializer() {
    }

    public MessageSerializer(VerifiableProperties properties) {
        log.debug("serializer properties ：{}", properties);
    }

    public V fromBytes(byte[] bytes) {
        V obj = null;
        ByteArrayInputStream bis = null;
        ObjectInputStream ois = null;

        try {
            bis = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bis);
            obj = (V) ois.readObject();
        } catch (IOException var16) {
            log.error("message unSerializer IOException：{}", var16);
        } catch (ClassNotFoundException var17) {
            log.error("message serializer ClassNotFoundException：{}", var17);
        } finally {
            try {
                if(ois != null) {
                    ois.close();
                }

                if(bis != null) {
                    bis.close();
                }
            } catch (IOException var15) {
                log.error("message unSerializer IOException：{}", var15);
            }

        }

        return obj;
    }

    public byte[] toBytes(V o) {
        byte[] bytes = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;

        try {
            oos = new ObjectOutputStream(bos);
            oos.writeObject(o);
            oos.flush();
            bytes = bos.toByteArray();
        } catch (IOException var14) {
            log.error("message serializer IOException：{}", var14);
        } finally {
            try {
                if(oos != null) {
                    oos.close();
                }

                if(bos != null) {
                    bos.close();
                }
            } catch (IOException var13) {
                log.error("message serializer IOException：{}", var13);
            }

        }

        return bytes;
    }
}
