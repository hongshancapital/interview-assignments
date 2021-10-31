package com.example.shortlink.generator;


/**
 * the Generator generates an global id. But we don't use it in
 * service layer director , we wrap it as "Galting" (aka galting),
 * we don't need to care the specific Generator and how to configure
 * them .
 *
 *
 * we provide some types of generators to encounter with the stand-alone and
 * distributed scenario . the Generator hierarchy as follows :
 *
 *     GlobalIdGenerator:
 *             |
 *             |_  StandAloneGenerator     : stand alone scenario
 *             |    |_AtomicLongGenerator  : use {@code AtomicLong} to generate id
 *             |
 *             |
 *             |—— DistributedGenerator    : distributed scenario
 *                 |_ SnowFlakeGenerator   : snow flake  algorithm-liked generator , here
 *                                           we use [host_id]_[time_stamp]_[num] as id format
 *
 *
 *
 *
 * @param <T> the type of Generator returns
 */
public interface GlobalIdGenerator<T> {


    /**
     * generate id.
     *
     *
     * @return the id.
     */
    T getId();
}
