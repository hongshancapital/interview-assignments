package com.liukun.shortdomain.utils;

/**
 * <p>
 * <b>Class name</b>:
 * </p>
 * <p>
 * <b>Class description</b>: Class description goes here.
 * </p>
 * <p>
 * <b>Author</b>: kunliu
 * </p>
 * <b>Change History</b>:<br/>
 * <p>
 *
 * <pre>
 * Date          Author       Revision     Comments
 * ----------    ----------   --------     ------------------
 * 2021/10/7       kunliu        1.0          Initial Creation
 *
 * </pre>
 *
 * @author kunliu
 * @date 2021/10/7
 * </p>
 */
public class SeqInstance {
    private SeqInstance() {

    }

    private GuidUtil guidUtil = new GuidUtil();

    private static class SeqInstanceHolder {
        private static SeqInstance seqInstance = new SeqInstance();
    }


    public long getSeq() {
        return guidUtil.getGuid();
    }

    public static SeqInstance getInstance() {

        return SeqInstanceHolder.seqInstance;

    }


}
