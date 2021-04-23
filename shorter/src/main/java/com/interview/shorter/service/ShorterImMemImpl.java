
package com.interview.shorter.service;

import com.interview.shorter.commons.Hash;
import com.interview.shorter.commons.Helper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Bai Lijun mailTo: 13910160159@163.com
 * Created at 2021-04-23
 */
/**
 * 短码转换内存实现
 */
@Service
public class ShorterImMemImpl implements Shorter {

    private static Map<String, String> InMem = new ConcurrentHashMap<>();
    private static Logger log = LoggerFactory.getLogger(ShorterImMemImpl.class);


    private int length = 8;
    private int radix = 62;
    private static long capacity = -1;

    final static char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8',
            '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
            'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
            'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
            'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y',
            'Z'};

    final static String HTTP_START_EXP = "^http[s]?://.*";


    @Override
    public String shorting(int type, String source) {
        if (StringUtils.isEmpty(source)) {
            throw new IllegalArgumentException("参数不能为空");
        }


        if (!source.matches(HTTP_START_EXP)) {
            source = "http://".concat(source);
        }


        String id = null;
        try {

            for (int i = 0; i < 8; i++) {
                id = genId(source, i);
                log.debug("产生id", id, i);
                if (!InMem.containsKey(id)) {
                    break;
                }
                Atlas atlas = this.restore(id);
                if (atlas.match(source)) {
                    return id;
                }
            }

            // collision !!!
            while (StringUtils.isEmpty(id)) {
                id = Helper.random(getLength() + 1, digits);

                log.debug("!!!!!!Generated candidate of id {0}.", id);

                if (!InMem.containsKey(id)) {
                    break;
                }
            }

            //long[] aux = Helper.hash(source);
            try {
                InMem.put(id, source);
            } catch (RuntimeException dae) {
                if (!InMem.containsKey(id)) {
                    throw dae;
                }

                Atlas atlas = this.restore(id);
                if (atlas.match(source)) {
                    return id;
                } else {
                    return shorting(type, source);// later collision!!
                }
            }
        } finally {

        }

        return id;
    }

    @Override
    public boolean hasExist(String id) {
        return InMem.containsKey(id);

    }

    @Override
    public Body replace(String id) {
        String value = InMem.get(id);
        return new Body() {
            @Override
            public int getType() {
                return 0;
            }

            @Override
            public String getContent() {
                return value;
            }

            @Override
            public Body reset() {
                return this;
            }

        };
    }

    @Override
    public Atlas restore(String id) {
        String value = InMem.get(id);
        return new AtlasImpl(id, value);
    }

    @Override
    public int update(String id, String body) {
        InMem.put(id, body);
        return 0;

    }

    public class AtlasImpl implements Atlas {
        String id;
        String value;
        private long[] old;

        public AtlasImpl(String id, String value) {
            this.id = id;
            this.value = value;
            old = Helper.hash(value);
        }

        @Override
        public int getType() {
            return 0;
        }

        @Override
        public String getContent() {
            return value;
        }

        @Override
        public Body reset() {

            return this;
        }

        @Override
        public String getId() {
            return id;
        }

        @Override
        public long getKey() {
            return -1L;
        }

        @Override
        public long getAux() {
            return -1L;
        }

        @Override
        public long getAux1() {
            return -1L;
        }

        @Override
        public Date getCreateAt() {
            return new Date();
        }

        @Override
        public boolean match(String source) {
            long[] sh = Helper.hash(source);
            if (sh == null) {
                return false;
            }


            return sh[0] == old[0] && sh[1] == old[1] && sh[2] == old[2];
        }

    }

    private String genId(String source, int seq) {
        if (capacity < 0) {
            if (getLength() > 1 && getLength() != 6) {
                capacity = ((Double) Math.pow(getRadix(), getLength()))
                        .longValue();
            } else {
                capacity = 56800235584L;
            }
        }

        byte[] s = source.getBytes();
        long key = Hash.longHash(s);

        switch (seq) {
            case 0:
                key = Math.abs(Hash.transMirror(s) - key);
                break;
            case 1:
                key = Math.abs(key + Hash.transMirror(s));
                break;
            case 2:
                key = Math.abs(key - Hash.trans(s));
                break;
            case 3:
                key = Math.abs(key + Hash.trans(s));
                break;
            case 4:
                key = Math.abs(key - Hash.mirror(s));
                break;
            case 5:
                key = Math.abs(key + Hash.mirror(s));
                break;

            default:
                break;
        }

        return Helper.toString(key % capacity, getRadix());
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getRadix() {
        return radix;
    }

    public void setRadix(int radix) {
        this.radix = radix;
    }

}
