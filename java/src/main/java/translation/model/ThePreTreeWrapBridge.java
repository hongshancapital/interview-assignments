package translation.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import translation.util.Constants;
import translation.util.JvmNeiCunTool;
import translation.util.ShortLongUrlUtils;
import translation.util.UuidUtil;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.*;

/**
 * 前缀树外包装
 * 桥接模式 .. 当然比较简单; 没有定义什么接口
 * 里面内置的 存取数据结构为 tree
 * 没用map(太消耗资源)
 *
 * @author: hello
 * @since: 2023/2/21
 */
@Component
public class ThePreTreeWrapBridge {
    ThePreTree tree = new ThePreTree();
    private final static Logger log = LoggerFactory.getLogger(ThePreTreeWrapBridge.class);
    //默认只能缓存3天
    @Value("${cacheDay:3}")
    int cacheDay = 3;
    //循环获得 shortWord的次数;
    int whileTime = 10;

    public ThePreTreeWrapBridge() {

    }

    public boolean addOrSet(UrlMapBean urlMapBean) {
        boolean flag = false;
        String flagStr = null;
        ThePreTree.PreNode node = null;
        String wordOld = urlMapBean.getShortUrl();
        String wordNew = null;
        flagStr = tree.addOrSet(wordOld, urlMapBean);
        if (Constants.REPET.equals(flagStr)) {
            node = tree.getPreNode(wordOld);
            wordNew = getTheWordNew(urlMapBean);
            if (wordNew == null) {
                throw new RuntimeException("还是短key冲突需要改善算法");
            } else {
                node.isCrash = false;
                urlMapBean.setShortUrl(wordNew);
                tree.addOrSet(wordNew, urlMapBean);
            }
        }
        flag = true;
        return flag;
    }

    public UrlMapBean get(String shortUrl) {
        return tree.get(shortUrl);
    }

    private String getTheWordNew(UrlMapBean urlMapBean) {
        String wordNew = null;
        for (int i = 0; i < whileTime; i++) {
//            log.info("本轮有冲突,重新获得key的次数为:" + i);
            wordNew = ShortLongUrlUtils.longToShort(urlMapBean.getLongUrl(), UuidUtil.toUuid());
            if (!tree.contains(wordNew)) {
                return wordNew;
            }
        }

        return wordNew;
    }

    /**
     * 这里可以用作定时任务的调度
     */
    public void clearClear() {
        log.info("执行清理(可以用xxl-job)");
        tree.clearCache(cacheDay);
    }

    /**
     * 测试系统稳健性 专门的测试代码 写在这里图个方便;
     */
    public void test(Integer t) throws Exception {
        int time = t == null ? 100000 : t;
        String longUrlMap = null;//用于对比测试的
        String longUrlTree = null;//用于对比测试的
        Map<String, String> hashMap = new ConcurrentHashMap<>();
        UrlMapBean urlMapBean = null;
        int poolSize = Runtime.getRuntime().availableProcessors() * 2;
        int totalNum = time * poolSize;
        long start = System.currentTimeMillis();
        log.info("开始进行压测;压测前内存情况为:");
        JvmNeiCunTool.showRuntimeNeiCun(Runtime.getRuntime());
        log.info("测试单个线程量消耗的数量为:{};启动的线程数为:{};总数据量为:{};现在的时间为:{};"
                , time, poolSize, totalNum, start);
        ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.newFixedThreadPool(poolSize);
        List<Future> list = new LinkedList<>();
        for (int i = 0; i < poolSize; i++) {
            list.add(executorService.submit(
                    () -> {
                        String shortUrlRun = null;
                        String longUrlRun = null;
                        UrlMapBean urlMapBeanRun = null;
//                        log.info("线程:"+Thread.currentThread().getName()+";启动遍历...");
                        //运用 map进行存储方便后面的比对
                        for (int j = 0; j < time; j++) {
                            longUrlRun = UUID.randomUUID().toString();
                            shortUrlRun = ShortLongUrlUtils.longToShort(longUrlRun, null);
                            urlMapBeanRun = new UrlMapBean(longUrlRun, shortUrlRun);
                            ThePreTreeWrapBridge.this.addOrSet(urlMapBeanRun);
                            hashMap.put(urlMapBeanRun.getShortUrl(), longUrlRun);
                        }
                    }
                    )
            );
        }
        list.forEach(x -> {
            try {
                x.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });
        if (hashMap.keySet().size() != totalNum) {
            log.error("创建的shortKey以及存放有重复的注意...");
        }
        executorService.shutdown();
        long addOver = System.currentTimeMillis();
        log.info("新增完成,现在的时间为:{};线程数:{};每个线程新增量为:{};总计数:{};消耗时间:{};"
                , addOver, poolSize, time, totalNum, addOver - start);
        long plStart = System.currentTimeMillis();
        log.info("进行tree的测试看是否有shortUrl碰撞,即为每个shortUrl都是唯一的:现在的时间为:{};", plStart);
        tree.printLevel();
        long plOver = System.currentTimeMillis();
        log.info("进行tree的测试看是否有shortUrl碰撞-Over,即为每个shortUrl都是唯一的:现在的时间为:{};消耗时间为:{};"
                , plOver, plOver - plStart);
        long sjStart = System.currentTimeMillis();
        log.info("进行数据映射正确的验证(即为1个长链接只对应唯一的1个短链接);现在的时间为:{};"
                , sjStart);
        //进行对比
        for (String k : hashMap.keySet()) {
            urlMapBean = tree.get(k);
            if (urlMapBean == null) {
                throw new Exception("映射缺失");
            }
            longUrlMap = hashMap.get(k);
            longUrlTree = urlMapBean.getLongUrl();
            if (!longUrlMap.equals(longUrlTree)) {
                throw new Exception("映射不对:longUrlMap为:" + longUrlMap + ";longUrlTree为:" + longUrlTree);
            }
        }
        long sjOver = System.currentTimeMillis();
        log.info("进行数据映射正确的验证(即为1个长链接只对应唯一的1个短链接)-OVER;现在的时间为:{};该阶段消耗时间为:{};"
                , sjOver, sjOver - sjStart);
        System.out.println(tree.getPreNode(urlMapBean.getShortUrl()));
        urlMapBean.setCreatIime(urlMapBean.getCreatIime());
        urlMapBean.setClickRate(urlMapBean.getClickRate());
        urlMapBean.setCreatIime(System.currentTimeMillis());
        urlMapBean.setLongUrl("代码覆盖率");
        System.out.println(urlMapBean);
        UuidUtil.toUuid();
        urlMapBean = new UrlMapBean();
        log.info("测试结束-成功;抗压能力通过;最后tree的信息为:{};所有测试阶段耗时:{};", this.tree, sjOver - start);
        log.info("进行压测接受之后;压测后内存情况为:");
        JvmNeiCunTool.showRuntimeNeiCun(Runtime.getRuntime());
    }


}