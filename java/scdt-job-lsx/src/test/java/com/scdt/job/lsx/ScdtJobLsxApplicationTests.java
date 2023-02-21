package java.com.scdt.job.lsx;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.hash.Hashing;
import org.junit.jupiter.api.Test;

//@SpringBootTest
class ScdtJobLsxApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    public void murmur3() {

//        long hashCode = Hashing.murmur3_32().hashBytes("www.baidu.com".getBytes(StandardCharsets.UTF_8)).asLong();
        int l = Hashing.murmur3_32().hashUnencodedChars("https://www.baidu.com/s?wd=Murmur%20%E7%9F%AD%E5%9F%9F%E5%90%8D%20%20java&rsv_spt=1&rsv_iqid=0xf75206b0000699be&issp=1&f=8&rsv_bp=1&rsv_idx=2&ie=utf-8&rqlang=cn&tn=baiduhome_pg&rsv_enter=1&rsv_dl=tb&oq=Murmur%2520%25E7%259F%25AD%25E5%259F%259F%25E5%2590%258D&rsv_t=b474lk9oBR72NbCHt9XcMQy8ASkFTOO2VyaqflatGHQurZCPgoHOsDOuzHjjhrZfSgma&rsv_btype=t&inputT=1878&rsv_pq=ca91c612000edbc6&rsv_sug3=85&rsv_sug2=0&rsv_sug4=1878").asInt();
        System.out.println(l);
    }

    @Test
    public void bimap() {
        BiMap<String, String> biMap = HashBiMap.create();

        biMap.put("lsx", "123");
        if (biMap.containsKey("lsx")) {

            System.out.println(biMap.get("lsx"));
        }else {

            biMap.put("lsx", "456");
        }

    }

}
