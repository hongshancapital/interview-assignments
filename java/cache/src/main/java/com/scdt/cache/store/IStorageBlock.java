package com.scdt.cache.store;

import java.io.Closeable;
import java.io.IOException;

public interface IStorageBlock extends Closeable {
    byte[] get(Pointer pointer) throws IOException;

    byte[] remove(Pointer pointer) throws IOException;

    Pointer store(byte[] payload) throws IOException;

    Pointer update(Pointer pointer, byte[] payload) throws IOException;
}
