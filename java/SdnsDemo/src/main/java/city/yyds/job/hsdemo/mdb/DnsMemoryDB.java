package city.yyds.job.hsdemo.mdb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DnsMemoryDB {
    private static Logger log = LoggerFactory.getLogger(DnsMemoryDB.class);

    /**
     * 保存数据的顺序号
     */
    private static Long SERIAL_NUMBER = 0l;
    /**
     * 记录最大个数 64的8次方为 281474976710656l
     */
    private static final Long RECORD_MAX_NUMBER = 1000l;
    /**
     * 当前记录个数
     */
    private static Long CURRENT_SIZE = 0l;
    /**
     * 时间一分钟
     */
    static final Long ONE_MINUTE =  60*1000L;
    /**
     * 记录对象
     */
    private static final Map<String, RecordInfo> RECORD_INFO_MAP = new ConcurrentHashMap<>();
    /**
     * 这个记录了记录使用的最后一次的记录，最近使用的在最前面
     */
    private static final List<String> RECORD_USE_LOG_LIST = new LinkedList<>();
    /**
     * 清理过期记录是否在运行
     */
    private static volatile Boolean CLEAN_THREAD_IS_RUN = false;
    /**
     * 执行到期清理的线程
     */
    private static Thread CLEAN_THREAD;

    public static Long increment(){
        return SERIAL_NUMBER++;
    }
    /**
     * 设置记录
     */
    public static void saveRecord(String recordKey, String recordValue, long recordTime) {
        Long ttlTime = null;
        if (recordTime <= 0L) {
            if (recordTime == -1L) {
                ttlTime = -1L;
            } else {
                return;
            }
        }
        checkSize();
        saveRecordUseLog(recordKey);
        CURRENT_SIZE = CURRENT_SIZE + 1;
        if (ttlTime == null) {
            ttlTime = System.currentTimeMillis() + recordTime;
        }
        RecordInfo recordInfo = new RecordInfo(recordValue, ttlTime);
        RECORD_INFO_MAP.put(recordKey, recordInfo);
        log.debug("have set key :" + recordKey);
    }

    /**
     * 设置记录
     */
    public static void saveRecord(String recordKey, String recordValue) {
        saveRecord(recordKey, recordValue, -1L);
    }

    /**
     * 获取记录
     */
    public static String selectRecord(String recordKey) {
        startCleanThread();
        if (checkRecord(recordKey)) {
            saveRecordUseLog(recordKey);
            return RECORD_INFO_MAP.get(recordKey).getRecordValue();
        }
        return null;
    }

    public static boolean isExist(String recordKey) {
        return checkRecord(recordKey);
    }

    /**
     * 删除所有记录
     */
    public static void clear() {
        log.debug("have clean all key !");
        RECORD_INFO_MAP.clear();
        CURRENT_SIZE = 0l;
    }

    /**
     * 删除某个记录
     */
    public static void deleteRecord(String recordKey) {
        RecordInfo recordInfo = RECORD_INFO_MAP.remove(recordKey);
        if (recordInfo != null) {
            log.debug("have delete key :" + recordKey);
            CURRENT_SIZE = CURRENT_SIZE - 1;
        }
    }

    /**
     * 判断记录在不在,过没过期
     */
    private static boolean checkRecord(String recordKey) {
        RecordInfo recordInfo = RECORD_INFO_MAP.get(recordKey);
        if (recordInfo == null) {
            return false;
        }
        if (recordInfo.getTtlTime() == -1L) {
            return true;
        }
        if (recordInfo.getTtlTime() < System.currentTimeMillis()) {
            deleteRecord(recordKey);
            return false;
        }
        return true;
    }

    /**
     * 删除最近最久未使用的记录
     */
    private static void deleteLRU() {
        log.debug("delete Least recently used run!");
        synchronized (RECORD_USE_LOG_LIST) {
            String recordKey = RECORD_USE_LOG_LIST.remove(RECORD_USE_LOG_LIST.size() - 1);
            deleteRecord(recordKey);
        }
    }

    /**
     * 删除过期的记录
     */
    static void deleteTimeOut() {
        log.debug("delete time out run!");
        List<String> deleteKeyList = new LinkedList<>();
        for(Map.Entry<String, RecordInfo> entry : RECORD_INFO_MAP.entrySet()) {
            if (entry.getValue().getTtlTime() < System.currentTimeMillis() && entry.getValue().getTtlTime() != -1L) {
                deleteKeyList.add(entry.getKey());
            }
        }

        for (String deleteKey : deleteKeyList) {
            deleteRecord(deleteKey);
        }
        log.debug("delete record count is :" + deleteKeyList.size());

    }

    /**
     * 检查大小
     * 当当前大小如果已经达到最大大小
     * 首先删除过期记录，如果过期记录删除过后还是达到最大记录数目
     * 删除最久未使用记录
     */
    private static void checkSize() {
        if (CURRENT_SIZE >= RECORD_MAX_NUMBER) {
            deleteTimeOut();
        }
        if (CURRENT_SIZE >= RECORD_MAX_NUMBER) {
            deleteLRU();
        }
    }

    /**
     * 保存记录的使用记录
     */
    private static synchronized void saveRecordUseLog(String recordKey) {
        synchronized (RECORD_USE_LOG_LIST) {
            RECORD_USE_LOG_LIST.remove(recordKey);
            RECORD_USE_LOG_LIST.add(0,recordKey);
        }
    }

    /**
     * 设置清理线程的运行状态为正在运行
     */
    static void setCleanThreadRun() {
        CLEAN_THREAD_IS_RUN = true;
    }

    /**
     * 停止清理过期记录的线程
     */
    static void interruptCleanThread() {
        if (CLEAN_THREAD_IS_RUN) {
            CLEAN_THREAD.interrupt();
            CLEAN_THREAD_IS_RUN = false;
        }
    }
    /**
     * 开启清理过期记录的线程
     */
    private static void startCleanThread() {
        if (!CLEAN_THREAD_IS_RUN) {
            CleanTimeOutThread cleanTimeOutThread = new CleanTimeOutThread();
            CLEAN_THREAD = new Thread(cleanTimeOutThread);
            //设置为后台守护线程
            CLEAN_THREAD.setDaemon(true);
            CLEAN_THREAD.start();
        }
    }


}

class RecordInfo {
    /**
     * 记录对象
     */
    private String recordValue;
    /**
     * 记录过期时间
     */
    private Long ttlTime;

    RecordInfo(String recordValue, Long ttlTime) {
        this.recordValue = recordValue;
        this.ttlTime = ttlTime;
    }

    String getRecordValue() {
        return recordValue;
    }

    Long getTtlTime() {
        return ttlTime;
    }
}

/**
 * 每一分钟清理一次过期记录
 */
class CleanTimeOutThread implements Runnable{
    private static Logger log = LoggerFactory.getLogger(CleanTimeOutThread.class);
    @Override
    public void run() {
        DnsMemoryDB.setCleanThreadRun();;
        while (true) {
            log.debug("clean thread run ");
            DnsMemoryDB.deleteTimeOut();
            try {
                Thread.sleep(DnsMemoryDB.ONE_MINUTE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
