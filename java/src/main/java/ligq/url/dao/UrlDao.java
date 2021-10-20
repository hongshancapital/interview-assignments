package ligq.url.dao;

import cn.hutool.core.lang.Pair;
import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;
import org.rocksdb.RocksIterator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.function.Consumer;

import static ligq.url.Constants.*;
import static ligq.url.util.BytesUtil.*;

/**
 * rockdb的数据访问层
 * @author ligq
 * @since 2021-10-19
 */
@Repository
public class UrlDao {

    private static RocksDB rocksDB;
    @Value("${app.rocksdb.path}")
    private String path;

    @PostConstruct
    public void init() throws Exception {
        RocksDB.loadLibrary();
        Options options = new Options();
        options.setCreateIfMissing(true);
        rocksDB = RocksDB.open(options, path);
    }

    /** 根据id查询值 */
    public String getValue(int id) throws RocksDBException {
        byte[] bs = rocksDB.get(int2Bytes(id));
        if (bs == null) {
            return null;
        }
        return bytes2Str(bs).split(valSeparatorRegex)[1];
    }

    /** 遍布所有kv，并根据时间筛选，符合条件的调用func消费 */
    public void initRecentValues(long seconds, Consumer<Pair<Integer, String>> consumer) throws RocksDBException {
        RocksIterator iter = rocksDB.newIterator();
        int id = 0;
        String value = "";
        for (iter.seekToFirst(); iter.isValid(); iter.next()) {
            id = bytes2Int(iter.key());
            value = bytes2Str(iter.value());
            String[] vs = value.split(valSeparatorRegex);
            if (Long.parseLong(vs[0]) >= seconds) {
                consumer.accept(new Pair(id, vs[1]));
            }
        }
    }

    /** 存储kv，并在kv前拼接当前时间的秒数 */
    public void setValue(int id, String value) throws RocksDBException {
        long seconds = LocalDateTime.now().toEpochSecond(zone);
        value = seconds + valSeparator + value;
        rocksDB.put(int2Bytes(id), str2Bytes(value));
    }
}
