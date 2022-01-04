package me.huchao.sd.domain.url.model;

import lombok.extern.slf4j.Slf4j;
import me.huchao.sd.domain.DomainException;
import me.huchao.sd.domain.url.repos.SlotRepos;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author huchao
 */
@Slf4j
public class Bucket {

    private static final int SLOT_NAME_LENGTH = 2;

    private static final int CODE_LENGTH = 6;

    private Map<String, SlotHolder> slotContainer;

    private SlotRepos slotRepos;

    private int preFetchOffsetSize;

    private ScheduledExecutorService scheduledExecutorService ;

    private int watchPeriod;

    public Bucket(SlotRepos slotRepos, int preFetchOffsetSize, int watchPeriod) {
        super();
        if (null == slotRepos) {
            throw new IllegalArgumentException("slot repos of bucket can not be null");
        }
        if (preFetchOffsetSize <= 0) {
            throw new IllegalArgumentException("preFetchOffsetSize of bucket can not less than 1");
        }
        this.slotRepos = slotRepos;
        this.preFetchOffsetSize = preFetchOffsetSize;
        this.watchPeriod = watchPeriod;
        this.slotContainer = new ConcurrentHashMap<>();
        this.scheduledExecutorService = Executors.newScheduledThreadPool(1);
        this.scheduledExecutorService.scheduleAtFixedRate(() -> {
            while (true) {
                if (slotContainer == null) {
                    return;
                }
                try {
                    Iterator<Map.Entry<String, SlotHolder>> iter = slotContainer.entrySet().iterator();
                    while (iter.hasNext()) {
                        Map.Entry<String, SlotHolder> entry = iter.next();
                        SlotHolder slotHolder = entry.getValue();
                        if (System.currentTimeMillis() - slotHolder.lastTick.get() > 1000 * 60 * 30) {
                            iter.remove();
                        }
                    }
                } catch (Exception e) {
                    log.error("ex happened in bucket watch thread: {}", e.getMessage(), e);
                }
            }
        }, 1, this.watchPeriod, TimeUnit.SECONDS);
    }

    public Node getByCode(String code) {
        if (null == code || code.length() <= SLOT_NAME_LENGTH) {
            throw new IllegalArgumentException("code code can not be less than 3 chars");
        }
        String slotName = code.substring(0, 2);
        SlotHolder slotHolder = getSlotHolder(slotName);
        Slot slot = slotHolder.getSlot();
        return slot.getNodeByCode(code);
    }

    public Node getByOrigin(String origin) throws DomainException {
        if (null == origin || origin.isEmpty()) {
            throw new IllegalArgumentException("origin value can not be empty");
        }
        String md5 = DigestUtils.md5Hex(origin);
        String slotName = md5.substring(0, SLOT_NAME_LENGTH);
        SlotHolder slotHolder = getSlotHolder(slotName);
        Slot slot = slotHolder.getSlot();
        return slot.getNodeByOrigin(origin);
    }

    private SlotHolder getSlotHolder(String slotName) {
        SlotHolder slotHolder = this.slotContainer.get(slotName);
        if (slotHolder != null) {
            return slotHolder;
        }
        return initSlotHolder(slotName);
    }

    private synchronized SlotHolder initSlotHolder(String slotName) {
        SlotHolder slotHolder = this.slotContainer.get(slotName);
        if (slotHolder != null) {
            return slotHolder;
        }
        Slot slot = new Slot(slotName, CODE_LENGTH, this.slotRepos, this.preFetchOffsetSize);
        slotHolder = new SlotHolder(slot);
        this.slotContainer.put(slotName, slotHolder);
        return slotHolder;
    }

    private class SlotHolder {
        private Slot slot;
        private AtomicLong lastTick;

        public SlotHolder(Slot slot) {
            super();
            this.slot = slot;
            this.lastTick = new AtomicLong(System.currentTimeMillis());
        }

        public Slot getSlot() {
            this.lastTick.set(System.currentTimeMillis());
            return this.slot;
        }
    }
}
