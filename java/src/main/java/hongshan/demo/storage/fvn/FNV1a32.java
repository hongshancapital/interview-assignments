package hongshan.demo.storage.fvn;

/**
 * FNV算法获取结果四字节结果
 * 
 * @class hongshan.demo.storage.fvn.FNV1a32
 * @author 许坤
 * @email klxukun@126.com
 * @time 2021年10月18日 下午10:36:37
 *
 */
public class FNV1a32 extends FNV1 {

	public FNV1a32() {
		INIT = FNV1_32_INIT;
	}

	public long getHash() {
		return (hash & 0x00000000ffffffffL);
	}

	protected long fnv(byte[] buf, int offset, int len, long seed) {
		for (int i = offset; i < offset + len; i++) {
			seed ^= buf[i];
			seed += (seed << 1) + (seed << 4) + (seed << 7) + (seed << 8) + (seed << 24);
		}
		return seed;
	}
}
