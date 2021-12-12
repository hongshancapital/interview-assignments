package com.scdt.cache.store;

import org.junit.After;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class FileChannelStorageTest {

    private IStorage fileChannelStorage = null;

    @Test
    public void testFileChannelStorage() throws IOException {
        fileChannelStorage = new FileChannelStorage("/temp/cache", 16 * 1024 * 1024);

        String testString = "Test String";
        fileChannelStorage.put(0, testString.getBytes());
        byte[] dest = new byte[testString.getBytes().length];
        fileChannelStorage.get(0, dest);

        assertEquals(testString, new String(dest));

        // test limit items
        int limit = 10000;
        int[] positionArray = new int[limit];
        int[] lengthArray = new int[limit];
        int position = 0;
        for(int i = 0; i < limit; i++) {
            byte[] src = (testString + i).getBytes();
            positionArray[i] = position;
            lengthArray[i] = src.length;
            fileChannelStorage.put(position, src);
            position += src.length;
        }

        for(int i = 0; i < limit; i++) {
            dest = new byte[lengthArray[i]];
            fileChannelStorage.get(positionArray[i], dest);
            assertEquals(testString + i, new String(dest));
        }
    }

    @After
    public void clear() throws IOException {
        if (this.fileChannelStorage != null) {
            this.fileChannelStorage.close();
        }
    }
}