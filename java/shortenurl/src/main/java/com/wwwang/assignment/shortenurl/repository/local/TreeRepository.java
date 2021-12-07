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

    private AbstractShorten shorten;

    private Map<Character,Character> possibleChars = new HashMap<>();

    private TreeNode root;

    private Map<TreeNode,TreeNode> leafNodeMap = new ConcurrentHashMap<>();

    private ReentrantLock lock = new ReentrantLock();

    public TreeRepository(AbstractShorten shorten){
        root = new TreeNode();
        this.shorten = shorten;
    }

    @Override
    public String getLongUrl(String key) {
        TreeNode dummyNode = new TreeNode();
        dummyNode.shortUrl = key.toCharArray();
        TreeNode leafNode = leafNodeMap.get(dummyNode);
        if(leafNode == null){
            log.error("无效短链接--" + key);
            throw new BizException("无效短链接");
        }
        StringBuffer sb = new StringBuffer();
        while (leafNode.parent!=null && leafNode.value != '\0'){
            sb.append(leafNode.value);
            leafNode = leafNode.parent;
        }
        return sb.reverse().toString();
    }

    @Override
    public String getShortUrl(String key){
        char[] keyChars = key.toCharArray();
        TreeNode parent = root;
        TreeNode keyLastNode = null;
        for(int i=0; i<keyChars.length; i++){
            TreeNode node;
            if(i<keyChars.length-1){
                node = buildNode(keyChars[i],parent,false,key);
            }else {
                node = buildNode(keyChars[i],parent,true,key);
                keyLastNode = node;
            }
            parent = node;
        }
        return new String(keyLastNode.shortUrl);
    }

    private TreeNode buildNode(char nodeVal,TreeNode parent,boolean isLast,String longUrl) {
        TreeNode node;
        lock.lock();
        try {
            if (parent.subNodes.get(nodeVal) != null) {
                node = parent.subNodes.get(nodeVal);
            } else {
                node = new TreeNode();
                Character value;
                if (possibleChars.get(nodeVal) != null) {
                    value = possibleChars.get(nodeVal);
                } else {
                    value = new Character(nodeVal);
                    possibleChars.put(value, value);
                }
                node.value = value;
                node.parent = parent;
                parent.subNodes.put(value, node);
            }
        } finally {
            lock.unlock();
        }
        if (isLast) {
            if (node.shortUrl == null) {
                //调用生成短链接的方法
                node.shortUrl = shorten.shorten(longUrl).toCharArray();
                leafNodeMap.put(node, node);
            }
        }
        return node;
    }
}
