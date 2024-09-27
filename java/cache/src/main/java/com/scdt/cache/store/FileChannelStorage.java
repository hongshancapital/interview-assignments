package com.scdt.cache.store;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.time.LocalDateTime;

/**
 * 基于文件包装的，存储访问器
 */
public class FileChannelStorage implements IStorage {
    private FileChannel fileChannel;
    private RandomAccessFile raf;

    public FileChannelStorage(String dir, int capacity) throws IOException {
        File dirFile = new File(dir);
        if (!dirFile.exists()) { dirFile.mkdirs(); }
        String fullFileName = dir + System.currentTimeMillis() + DATA_FILE_SUFFIX;
        raf = new RandomAccessFile(fullFileName, "rw");
        raf.setLength(capacity);
        fileChannel = raf.getChannel();
    }

    @Override
    public void get(int position, byte[] dest) throws IOException {
        fileChannel.read(ByteBuffer.wrap(dest), position);
    }

    @Override
    public void put(int position, byte[] source) throws IOException {
        fileChannel.write(ByteBuffer.wrap(source), position);
    }

    @Override
    public void close() throws IOException {
        if (this.fileChannel != null) {
            this.fileChannel.close();
        }
        if (this.raf != null) {
            this.raf.close();
        }
    }
}
