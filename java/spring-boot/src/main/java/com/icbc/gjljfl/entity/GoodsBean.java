package com.icbc.gjljfl.entity;

/**
 * @author gaowh
 * @date 2021/4/30 16:50
 */
public class GoodsBean {
    private String id;
    private String name;
    private String stockSmall;
    private String stockBig;
    private String alreadyAchangedSmall;
    private String alreadyAchangedBig;
    private String upperShelf;
    private String communityId;

    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStockSmall() {
        return stockSmall;
    }

    public void setStockSmall(String stockSmall) {
        this.stockSmall = stockSmall;
    }

    public String getStockBig() {
        return stockBig;
    }

    public void setStockBig(String stockBig) {
        this.stockBig = stockBig;
    }

    public String getAlreadyAchangedSmall() {
        return alreadyAchangedSmall;
    }

    public void setAlreadyAchangedSmall(String alreadyAchangedSmall) {
        this.alreadyAchangedSmall = alreadyAchangedSmall;
    }

    public String getAlreadyAchangedBig() {
        return alreadyAchangedBig;
    }

    public void setAlreadyAchangedBig(String alreadyAchangedBig) {
        this.alreadyAchangedBig = alreadyAchangedBig;
    }

    public String getUpperShelf() {
        return upperShelf;
    }

    public void setUpperShelf(String upperShelf) {
        this.upperShelf = upperShelf;
    }
}
