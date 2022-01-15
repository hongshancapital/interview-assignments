package org.example.config;

public class URLChain {

    private URLNode headURLNode = new URLNode(null, null);
    private URLNode tailURLNode = new URLNode(null, null);

    public URLChain(){
        this.headURLNode.next = this.tailURLNode;
        this.tailURLNode.pre = this.headURLNode;
    }

    public URLNode getTailNode(){
        return tailURLNode.pre;
    }

    public URLNode addNewNode(URLNode newURLNode){
        URLNode headNextURLNode = headURLNode.next;

        newURLNode.next = headURLNode.next;
        newURLNode.pre = headURLNode;

        headURLNode.next = newURLNode;
        headNextURLNode.pre = newURLNode;
        return newURLNode;
    }

    public void change2HeadNode(URLNode urlNode){
        this.removeNode(urlNode);

        urlNode.pre = headURLNode;
        urlNode.next = headURLNode.next;

        headURLNode.next = urlNode;
    }

    public void removeNode(URLNode rURLNode){
        URLNode nextURLNode = rURLNode.next;
        nextURLNode.pre = rURLNode.pre;

        URLNode preURLNode = rURLNode.pre;
        preURLNode.next = rURLNode.next;
    }
}
