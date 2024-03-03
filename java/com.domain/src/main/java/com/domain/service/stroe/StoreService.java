package com.domain.service.stroe;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.domain.config.GlobalParametersConfig;
import com.domain.po.StorePO;
import com.domain.utils.Hash;
import com.domain.utils.LRUCache;
import com.domain.utils.MappedFile;
import com.domain.utils.ServiceThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 数据存储服务
 * @author jacky
 * @version 1.0
 * @since 1.0
 */
@Service
public class StoreService {

    private static LRUCache[] STROES;  //JVM 存储缓存  槽来存储 避免多个map 扩容导致性能降低

    @Autowired
    private GlobalParametersConfig globalParametersConfig;  //全局配置

    private static  final  String indexFileName="index";  //索引文件名称
    private static MappedFile indexMappedFile;  //索引文件
    private static  final int indexFileSize=1024;

    private static  final String positionFileName="position";  //指针文件名称
    private static MappedFile positionMappedFile;  //写指针文件
    private static  final int positionFileSize=1024;

    private static MappedFile writeMappedFile;  //当前持久化数据文件
    private static final int dataFileSize=1024 * 1024 * 4;

    private static int dataFileName=100000001;  //初始化文件名称

    private static final String Separator="$"; //分割符号

    private static int slots=1;  //槽位

    private ConcurrentLinkedQueue<StorePO> queue=new ConcurrentLinkedQueue<StorePO>();  //持久化队列

    private WriteDataThread writeDataThread; //持久化刷盘线程



    public StoreService(){
        this.writeDataThread = new WriteDataThread();
    }
    /**
     * spring init 回调
     */
    @PostConstruct
    public void init(){
        initStroe(); //初始化缓存
        initIndexFile();  //读取索引文件  获取最新持久化文件
        initPositionFile(); //读取写指针文件 获取最新持久化文件写指针
        initDataFile(); //读取最新持久化文件数据 存入缓存
        persistentData(); //读取历史数据 存入缓存
        start();
    }
    /**
     * 存储服务启动
     */
    public void start(){
        this.writeDataThread.start();  //启动刷盘服务
    }
    /**
     * 存储服务启动
     */
    public void shutdown(){
        this.writeDataThread.shutdown();  //停止刷盘服务
    }
    /**
     * 初始化 槽位缓存
     */
    private void initStroe(){
        slots=Integer.parseInt(globalParametersConfig.getSlots());
        int capacity=Integer.parseInt(globalParametersConfig.getCapacity());
        STROES=new  LRUCache[slots];
        for(int i=0;i<slots;i++){
            STROES[i]=new LRUCache<String,StorePO>(capacity);  //使用LRU 控制单个槽的 扩容  这样会淘汰些历史数据
        }
    }
    /**
     * 初始化 索引文件
     */
    private void initIndexFile(){
        File dir=new File(globalParametersConfig.getConfigFilePath());
        if(!dir.exists())dir.mkdirs();
        indexMappedFile=new MappedFile(globalParametersConfig.getConfigFilePath(),indexFileName,indexFileSize);
    }
    /**
     * 初始化 最后写指针文件
     */
    private void initPositionFile(){
        File dir=new File(globalParametersConfig.getConfigFilePath());
        if(!dir.exists())dir.mkdirs();
        positionMappedFile=new MappedFile(globalParametersConfig.getConfigFilePath(),positionFileName,positionFileSize);
    }
    /**
     * 初始化 最后持久化文件
     */
    private void initDataFile(){
        File dir=new File(globalParametersConfig.getDataFilePath());
        if(!dir.exists())dir.mkdirs();

        String filename=indexMappedFile.readData();
        if(filename!=null)filename=filename.trim();
        if(StringUtils.isEmpty(filename))filename=String.valueOf(dataFileName);
        writeMappedFile=new MappedFile(globalParametersConfig.getDataFilePath(),filename,dataFileSize);

        //写入索引文件
        indexMappedFile.writeData(writeMappedFile.getFileName().getBytes(),0);

        String position=positionMappedFile.readData();
        if(position!=null)position=position.trim();
        if(!StringUtils.isEmpty(position))positionMappedFile.setWritePosition(Integer.parseInt(position));
        else positionMappedFile.setWritePosition(writeMappedFile.getWritePosition().intValue());


        readPersistentDataToCashe(writeMappedFile); //读取到缓存
    }

    /**
     *  持久化数据
     */
    private void persistentData(){
        String path=globalParametersConfig.getDataFilePath();  //数据目录
        List<Integer> fileNames = new ArrayList<>();
        File dir = new File(path);
        File[] array = dir.listFiles();
        for(File file:array){
            if(file.getName().equals(writeMappedFile.getFileName())) continue;  //排除当前主写文件
            Integer fileName=null;
            try{
                fileName=Integer.parseInt(file.getName());
            }catch(Exception e){
                continue;
            }
            if(fileName!=null)fileNames.add(fileName);
        }
        Collections.sort(fileNames);
        for(int i=fileNames.size()-1;i>=0;i--){
            //倒序读取文件 持久换缓存中
            MappedFile his_writeMappedFile=new MappedFile(globalParametersConfig.getDataFilePath(),String.valueOf(fileNames.get(i)),dataFileSize);
            readPersistentDataToCashe(his_writeMappedFile);
            his_writeMappedFile.close(); //清除内存映射
        }

    }
    /**
     * 持久化数据
     * @param mappedFile  持久化文件对象
     */
    private void readPersistentDataToCashe(MappedFile mappedFile){
         String filedata=mappedFile.readData();
         if(filedata!=null)filedata=filedata.trim();
         if(!StringUtils.isEmpty(filedata.trim())){
             String[] datas=filedata.split("\\"+Separator);
             for(String data:datas){
                 if(!StringUtils.isEmpty(data)){
                     byte[] bytes=Base64.getDecoder().decode(data.getBytes());
                     String base64=new String(bytes);
                     JSONObject jsonObject = JSON.parseObject(base64);
                     StorePO storePO=JSON.toJavaObject(jsonObject,StorePO.class);
                     push(storePO);
                 }
             }
         }
    }

    /**
     * 同步数据到缓存
     * @param storePO  数据对象
     * @return true 存储成功
     */
    private void push(StorePO storePO){
        int slot= Hash.hashSlots(storePO.getAddressCode(),slots);
        LRUCache<String,StorePO> lruCaches=STROES[slot];
        lruCaches.put(storePO.getAddressCode(),storePO);
    }
    /**
     * 存储数据到缓存
     * @param storePO  数据对象
     * @return true 存储成功
     */
    public boolean save(StorePO storePO){
        int slot= Hash.hashSlots(storePO.getAddressCode(),slots);
        LRUCache<String,StorePO> lruCaches=STROES[slot];
        lruCaches.put(storePO.getAddressCode(),storePO);
        queue.add(storePO); //加入到队列 持久化
        return true;
    }
    /**
     * 检查生成的code是否已经存在缓存中
     * @param storePO  数据对象
     * @return true 存储成功
     */
    public boolean check(StorePO storePO){
        int slot= Hash.hashSlots(storePO.getAddressCode(),slots);
        LRUCache<String,StorePO> lruCaches=STROES[slot];
        if(lruCaches.isEmpty()) return true;
        StorePO storeRight=lruCaches.get(storePO.getAddressCode());
        if(storeRight==null) return true;
        return false;
    }
    /**
     * 获取存储数据
     * @param code  存储key
     * @return StorePO 存储数据
     */
    public StorePO get(String code){
        int slot= Hash.hashSlots(code,slots);
        LRUCache<String,StorePO> lruCaches=STROES[slot];
        if(lruCaches.isEmpty()) return null;
        return lruCaches.get(code);
    }
    /**
     * 存储数据到持久化
     * @param context  写入数据
     * @return true 存储成功
     */
    private void saveDisk(String context){
        if(!writeMappedFile.writeData(context.getBytes())){
            //未写成功 需要创建新文件
            createNewWriteFile();
            writeMappedFile.writeData(context.getBytes());
        }
        //写入指针文件
        positionMappedFile.writeData(String.valueOf(writeMappedFile.getWritePosition().intValue()).getBytes(),0);

    }
    /**
     * 创建新的写入文件
     */
    private void createNewWriteFile(){
        Integer fileNameNumer=Integer.parseInt(writeMappedFile.getFileName());
        fileNameNumer++;
        //关闭老文件
        writeMappedFile.close();
        //关键新文件
        String filename=String.valueOf(fileNameNumer);
        writeMappedFile=new MappedFile(globalParametersConfig.getDataFilePath(),filename,dataFileSize);
        //写入索引文件
        indexMappedFile.writeData(writeMappedFile.getFileName().getBytes(),0);
        //写入指针文件
        positionMappedFile.writeData(String.valueOf(writeMappedFile.getWritePosition().intValue()).getBytes(),0);
    }
    /**
     * 写数据线程
     * @author jacky
     * @version 1.0
     * @since 1.0
     */
    class WriteDataThread extends ServiceThread{

        @Override
        public String getServiceName() {
            return WriteDataThread.class.getName();
        }

        @Override
        protected void isShutdown() {
            for(;;){
                //持久化同步 完成才能停机 避免数据丢失
               if(queue.size()==0)return;
            }
        }

        @Override
        public void run() {
              for(;;){
                  //自旋
                  StorePO storePO=queue.poll();
                  if(storePO!=null){
                      String base64  = Base64.getEncoder().encodeToString(JSON.toJSONString(storePO).getBytes());
                      saveDisk(base64+StoreService.Separator);
                  }
              }
        }

    }
}
