package com.wwwang.assignment.shortenurl.repository.local;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class TreeNode {

    public TreeNode parent;
    public char value;
    public Map<Character,TreeNode> subNodes = new HashMap();
    public char[] shortUrl;

    @Override
    public int hashCode() {
        return Arrays.hashCode(shortUrl);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        TreeNode treeNode = (TreeNode) obj;
        return Arrays.equals(this.shortUrl,treeNode.shortUrl);
    }

}
