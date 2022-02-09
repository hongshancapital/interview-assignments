package hongshan.demo.storage.fvn;

/**
 * FNV算法
 * 
 * @class hongshan.demo.storage.fvn.FNV1
 * @author 许坤
 * @email klxukun@126.com
 * @time 2021年10月18日 下午10:36:12
 *
 */
public abstract class FNV1 {
	public static final long FNV1_32_INIT = 0x811c9dc5L;
	public static final long FNV1_64_INIT = 0xcbf29ce484222325L;

	protected long INIT = 0L;
	protected long hash = 0L;

	public void init(String s) {
		byte[] buf = null;
		try {
			buf = s.getBytes("UTF-8");
		} catch (Exception e) {
			buf = s.getBytes();
		}
		init(buf, 0, buf.length);
	}

	public void init(byte[] buf, int offset, int len) {
		hash = fnv(buf, offset, len, INIT);
	}

	public void update(String s) {
		byte[] buf = null;
		try {
			buf = s.getBytes("UTF-8");
		} catch (Exception e) {
			buf = s.getBytes();
		}
		update(buf, 0, buf.length);
	}

	public void update(byte[] buf, int offset, int len) {
		hash = fnv(buf, offset, len, hash);
	}

	public long getHash() {
		return hash;
	}

	protected abstract long fnv(byte[] buf, int offset, int len, long seed);

}
