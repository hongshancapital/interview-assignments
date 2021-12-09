package com.wwwang.assignment.shortenurl.repository.local;

import com.wwwang.assignment.shortenurl.exception.BizException;
import com.wwwang.assignment.shortenurl.repository.RepoProvider;
import com.wwwang.assignment.shortenurl.shorten.AbstractShorten;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class TreeRepository implements RepoProvider {
    /**
     * 生成短链接的工具
     */
    private AbstractShorten shorten;

    /**
     * 可能出现的字符
     */
    private Map<Character,Character> possibleChars = new HashMap<>();

    /**
     * root节点
     */
    private TreeNode root;

    /**
     * 叶子节点的map，方便通过叶子节点中的短链接信息，遍历到长链接信息
     */
    private Map<TreeNode,TreeNode> leafNodeMap = new ConcurrentHashMap<>();

    /**
     * 节点信息维护的锁
     */
    private ReentrantLock lock = new ReentrantLock();

    public TreeRepository(AbstractShorten shorten){
        root = new TreeNode();
        this.shorten = shorten;
    }

    /**
     * 获取长链接
     * @param key 短链接
     * @return
     */
    @Override
    public String getLongUrl(String key) {
        //通过短链接信息去找到代表某一条长链接的叶子节点
        TreeNode dummyNode = new TreeNode();
        dummyNode.shortUrl = key.toCharArray();
        TreeNode leafNode = leafNodeMap.get(dummyNode);
        if(leafNode == null){
            log.error("无效短链接--" + key);
            throw new BizException("无效短链接");
        }
        //从叶子节点向上遍历到代表长链接的分支，拼接成长链接返回
        StringBuffer sb = new StringBuffer();
        while (leafNode.parent!=null && leafNode.value != '\0'){
            sb.append(leafNode.value);
            leafNode = leafNode.parent;
        }
        return sb.reverse().toString();
    }

    /**
     * 获取短链接
     * @param key 长链接
     * @return
     */
    @Override
    public String getShortUrl(String key){
        char[] keyChars = key.toCharArray();
        TreeNode parent = root;
        TreeNode keyLastNode = null;
        //将长链接转换为树中一条分支,按字符顺序
        for(int i=0; i<keyChars.length; i++){
            TreeNode node;
            if(i<keyChars.length-1){
                //最后一个字符是叶子节点
                node = buildNode(keyChars[i],parent,false,key);
            }else {
                node = buildNode(keyChars[i],parent,true,key);
                keyLastNode = node;
            }
            parent = node;
        }
        return new String(keyLastNode.shortUrl);
    }

    /**
     * 构建树中一个节点
     * @param nodeVal 节点值，也就是长链接各个位置的字符
     * @param parent 父节点
     * @param isLast 是不是最后一个字符
     * @param longUrl  长链接的字符串
     * @return
     */
    private TreeNode buildNode(char nodeVal,TreeNode parent,boolean isLast,String longUrl) {
        TreeNode node;
        //加锁操作
        lock.lock();
        try {
            //父节点的子节点中已经有了代表相同字符的节点，获取节点即可
            if (parent.subNodes.get(nodeVal) != null) {
                node = parent.subNodes.get(nodeVal);
            } else {//否则需要创建新的节点对象
                node = new TreeNode();
                Character value;
                if (possibleChars.get(nodeVal) != null) {
                    //维护可能出现的字符对象，防止重复创建对象，在节点中维护的子节点map需要字符对象作为key
                    value = possibleChars.get(nodeVal);
                } else {
                    value = new Character(nodeVal);
                    possibleChars.put(value, value);
                }
                node.value = nodeVal;
                node.parent = parent;
                //设置子节点
                parent.subNodes.put(value, node);
            }
        } finally {
            lock.unlock();
        }
        //最后一个字符节点，需要设置代表短链接的值的字符数组
        if (isLast) {
            if (node.shortUrl == null) {
                //调用生成短链接的方法
                node.shortUrl = shorten.shorten(longUrl).toCharArray();
                //叶子节点的map，方便通过短链接查找到长链接的叶子节点
                leafNodeMap.put(node, node);
            }
        }
        return node;
    }
}
