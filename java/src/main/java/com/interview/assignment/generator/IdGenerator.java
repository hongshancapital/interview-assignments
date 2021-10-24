package com.interview.assignment.generator;

import com.interview.assignment.model.GeneratedId;
import com.interview.assignment.request.ShortCodeGenerateRequest;

/**
 * id生成器，可以有多种生成方式：
 * 1. 内存id生成器；
 * 2. 缓存的内存Id生成器；
 * 3. 通过数据库插入一条数据生成一个id；
 * 4. 通过数据库一次性批量预生成一堆id；
 * 5. 通过redis的increment生成一个id；
 * 6. 使用类似于雪花算法的方式生成id；
 */
public interface IdGenerator extends Generator<ShortCodeGenerateRequest, GeneratedId> {
}
