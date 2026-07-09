package scdt.interview.java.service;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

@Service
public class SequenceService {

	private static AtomicLong NUM_SEQ = new AtomicLong(0);

	public static long getNextSeq() {
		return NUM_SEQ.getAndIncrement();
	}
}
