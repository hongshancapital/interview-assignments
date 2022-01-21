package indi.zixiu.shortdomainname.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ShortDomainNameGeneratorTest {
    private long workerId = 1;
    private long datacenterId = 0;

    private ShortDomainNameGenerator shortDomainNameGenerator = new ShortDomainNameGenerator(workerId, datacenterId);

    @Test
    public void testConstructor_givenInvalidArgument_thenException() {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new ShortDomainNameGenerator(-1, 0),
                "'workerId' < 0，应该抛异常");

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new ShortDomainNameGenerator(0, -1),
                "'datacenterId' < 0，应该抛异常");
    }

    @Test
    public void testNextShortDomainName() {
        long lastTimestamp = -1L;
        long lastSequence = 0L;

        Class<ShortDomainNameGenerator> shortDomainNameGeneratorClass = ShortDomainNameGenerator.class;
        long sequenceBits = shortDomainNameGenerator.sequenceBits;
        long maxSequenceCountInOneTimestamp = 1L << sequenceBits;

        // 生成 maxSequenceCountInOneTimestamp + 1 个短域名，确保短域名中的时间戳会发生变化
        for (int i = 0; i <= maxSequenceCountInOneTimestamp; i++) {
            long shortDomainName = shortDomainNameGenerator.nextShortDomainName();
            long[] shortDomainNameParts = decomposeShortDomainName(shortDomainName);
            long timestamp = shortDomainNameParts[0];
            long datacenterId = shortDomainNameParts[1];
            long workerId = shortDomainNameParts[2];
            long sequence = shortDomainNameParts[3];

            Assertions.assertEquals(this.datacenterId, datacenterId, "datacenterId");
            Assertions.assertEquals(this.workerId, workerId, "workerId");

            if (timestamp == lastTimestamp) {
                Assertions.assertEquals(lastSequence + 1, sequence, "时间戳没变，序号应加 1");
            } else {
                Assertions.assertEquals(0, sequence, "时间戳变了，序号应为 0");
            }

            lastTimestamp = timestamp;
            lastSequence = sequence;
        }
    }

    private long[] decomposeShortDomainName(long shortDomainName) {
        int partCount = 4;
        long[] shortDomainNameParts = new long[partCount];
        Class<ShortDomainNameGenerator> shortDomainNameGeneratorClass = ShortDomainNameGenerator.class;

        long[] partShifts = {
                shortDomainNameGenerator.timestampLeftShift,
                shortDomainNameGenerator.datacenterIdShift,
                shortDomainNameGenerator.workerIdShift,
                0
        };

        long[] partBits = {
                0,
                shortDomainNameGenerator.datacenterIdBits,
                shortDomainNameGenerator.workerIdBits,
                shortDomainNameGenerator.sequenceBits,
        };
        long longBits = 64;
        partBits[0] = longBits - partBits[1] - partBits[2] - partBits[3];

        long[] partMasks = new long[partCount];
        for (int i = 0; i < partCount; i++) {
            partMasks[i] = (-1L ^ (-1L << partBits[i])) << partShifts[i];
        }

        for (int i = 0; i < partCount; i++) {
            shortDomainNameParts[i] = (shortDomainName & partMasks[i]) >> partShifts[i];
        }

        return shortDomainNameParts;
    }

    @Test
    public void testTilNextMillis() {
        long lastTimestamp = System.currentTimeMillis();
        for (int i = 0; i < 5; i++) {
            long newTimestamp = shortDomainNameGenerator.tilNextMillis(lastTimestamp);
            Assertions.assertTrue(newTimestamp > lastTimestamp);
            lastTimestamp = newTimestamp;
        }
    }
}
