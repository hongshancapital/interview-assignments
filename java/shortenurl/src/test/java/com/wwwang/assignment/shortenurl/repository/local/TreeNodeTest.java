package com.wwwang.assignment.shortenurl.repository.local;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class TreeNodeTest {

    @Test
    public void test(){
        TreeNode node1 = new TreeNode();
        node1.shortUrl = new char[]{'a','b','c'};
        TreeNode node2 = new TreeNode();
        node2.shortUrl = new char[]{'a','b','c'};
        TreeNode node3 = new TreeNode();
        node3.shortUrl = new char[]{'a','b','c','d'};
        Assert.assertEquals(false, node1.equals(null));
        Assert.assertEquals(true, node1.equals(node1));
        Assert.assertEquals(node1,node1);
        Assert.assertEquals(false,node1.equals(node3));

        TreeNode node4 = new TreeNode();
        node4.shortUrl = new char[]{'Z','Z','M'};
        TreeNode node5 = new TreeNode();
        node5.shortUrl = new char[]{'Z','Z','M'};
        Assert.assertEquals(true,node4.equals(node5));

    }
}
