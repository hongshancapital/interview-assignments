package city.yyds.job.hsdemo.mdb;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DnsMemoryDBTest {
    private static final Logger log = LoggerFactory.getLogger(DnsMemoryDBTest.class);

    @Test
    void testDnsMemoryDB() {
        String key = "test:key";
        String value = "test:value";
        DnsMemoryDB.saveRecord(key,value);
        String result = DnsMemoryDB.selectRecord(key);
        Assert.assertEquals(value,result);

        String mkey = "test:m:key";
        DnsMemoryDB.saveRecord(mkey,value,-200);
        Assert.assertFalse(DnsMemoryDB.isExist(mkey));
        DnsMemoryDB.saveRecord(mkey,value,10*1000);
        Assert.assertTrue(DnsMemoryDB.isExist(mkey));
        DnsMemoryDB.deleteRecord(mkey);
        Assert.assertFalse(DnsMemoryDB.isExist(mkey));

        String nekey = "test:not:exist:key";
        Assert.assertFalse(DnsMemoryDB.isExist(nekey));
        String timeoutkey = "test:timeout:key";
        DnsMemoryDB.saveRecord(timeoutkey,value,1000);
        Assert.assertTrue(DnsMemoryDB.isExist(timeoutkey));
        try {
            Thread.sleep(1001);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        Assert.assertFalse(DnsMemoryDB.isExist(timeoutkey));
        DnsMemoryDB.clear();
        log.info("testDnsMemoryDB 执行完毕");
    }

    @Test
    void testCleanTimeOutThread() {
        DnsMemoryDB.setCleanThreadRun();
        String foreverkey = "test:forever:key";
        String value = "test:value";
        DnsMemoryDB.saveRecord(foreverkey,value,-1);
        for(int i=1; i<50; i++){
            DnsMemoryDB.saveRecord("key:"+i,value,500);
        }
        try {
            Thread.sleep(501);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        DnsMemoryDB.selectRecord(foreverkey);
        DnsMemoryDB.saveRecord("key:last",value);
        DnsMemoryDB.interruptCleanThread();
        DnsMemoryDB.interruptCleanThread();
        log.info("testDnsMemoryDB 执行完毕");
    }
    @Test
    void instantDnsMemoryDB() {
        DnsMemoryDB mdb = new DnsMemoryDB();
        mdb.getClass();
    }

}
