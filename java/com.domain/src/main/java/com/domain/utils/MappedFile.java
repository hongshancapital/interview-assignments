package com.domain.utils;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 堆外映射文件处理 乞丐版
 * @author jacky
 * @version 1.0
 * @since 1.0
 */
public class MappedFile {

    private AtomicInteger writePosition = new AtomicInteger(0);  //当前文件写指针
    private int fileSize;  //文件大小
    private FileChannel fileChannel;  //NIO 文件通道
    private String filePath;  //文件路劲
    private String fileName;  //文件名称
    private File file;
    private MappedByteBuffer mappedByteBuffer;  //物理文件对应的堆外内存映射buffer

    public MappedFile() {
    }
    public MappedFile(final String filePath,final String fileName, final int fileSize)  {
        init(filePath,fileName, fileSize);
    }

    public FileChannel getFileChannel(){
        return this.fileChannel;
    }
    public File getFile(){
        return this.file;
    }
    public String getFileName(){
        return this.fileName;
    }
    public String getFilePath(){
        return this.filePath;
    }
    public MappedByteBuffer getMappedByteBuffer(){
        return this.mappedByteBuffer;
    }
    public Integer getWritePosition(){
        return this.writePosition.intValue();
    }
    public void setWritePosition(int position){
         this.writePosition.addAndGet(position);
    }
    /**
     * 初始化
     * @param filePath  文件路径
     * @param fileName  文件名称
     * @param fileSize  初始化大小
     * @return
     */
    private void init(final String filePath,final String fileName, final int fileSize)  {
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
        this.file = new File(filePath+fileName);
        //判断上级目录是否存在 不存在创建
        if (this.file.getParent() != null) {
            File dir = new File(this.file.getParent());
            if (!dir.exists()) {
                boolean result = dir.mkdirs();
            }
        }
        try {
            this.fileChannel = new RandomAccessFile(this.file, "rw").getChannel();  //创建文件通道
            this.mappedByteBuffer = this.fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, fileSize); //对外内存申请
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    /**
     * 写数据
     * @param data  写数据
     * @return trun/false
     */
    public Boolean writeData(final byte[] data) {
        int currentPos = this.writePosition.get();  //写指针
        if ((currentPos+data.length) < this.fileSize) {  //如果写指针 小于 文件大小 还可以继续写
            try {
                this.fileChannel.position(currentPos);
                this.fileChannel.write(ByteBuffer.wrap(data));
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.writePosition.addAndGet(data.length);  //写指针扩容
            return true;
        }
        return false;
    }

    /**
     * 写数据 指定写指针
     * @param data  写数据
     * @param currentPos  写指针
     * @return trun/false
     */
    public Boolean writeData(final byte[] data,int currentPos) {
        try {
            this.fileChannel.position(currentPos);
            this.fileChannel.write(ByteBuffer.wrap(data));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    /**
     * 读数据 全部
     * @param
     * @return String
     */
    public String readData() {
        CharBuffer charBuffer = null;
        try{
            Charset charset = Charset.defaultCharset();
            CharsetDecoder decoder = charset.newDecoder();
            charBuffer = decoder.decode(mappedByteBuffer);
            mappedByteBuffer.flip();
            return charBuffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 读数据
     * @param readPosition  读指针
     * @param len  读取长度
     * @return String
     */
    public String readData(final int readPosition,final int len) {
        try {
            if((readPosition+len)<=this.fileSize){
                byte[] bytes=new byte[len+1];
                int index=0;
                for(int i=readPosition;i<=len;i++){
                    bytes[index]=mappedByteBuffer.get(i);
                }
                return new String(bytes);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 读数据
     * @param readPosition  读指针
     * @return String
     */
    public String readData(final int readPosition) {
        try {
            if(readPosition<this.fileSize){
                byte[] bytes=new byte[fileSize-readPosition+1];
                int index=0;
                for(int i=readPosition;i<=fileSize;i++){
                    bytes[index]=mappedByteBuffer.get(i);
                }
                return new String(bytes);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 关闭堆外映射
     */
    public void close() {
        if (this.fileChannel != null) {
            try {
                this.fileChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
