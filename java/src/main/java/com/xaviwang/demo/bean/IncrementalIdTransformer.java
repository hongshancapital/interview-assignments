package com.xaviwang.demo.bean;

public class IncrementalIdTransformer extends Transformer {

    /**
     * Object MAX_ID is to define the maximum of incremental ID. It's final object to avoid unexpected code change.
     */
    private final int MAX_ID = 99999999;
    
	public IncrementalIdTransformer() {
    }

	/**
     * Function transformFromOriginalUrlToTinyUrl provides the implementation of transforming original URL to Tiny URL, this method will override the one in parent class Transformer.
     * @param originalUrl, the original URL customer provides. 
     * @return result which is tiny URL being calculated according to the incremental algorithm.
     */
    @Override
    public String transformFromOriginalUrlToTinyUrl(String originalUrl) {
        String result = this.mapFromOriginalUrlToTinyUrl.get(originalUrl);
        if (result != null) {
        	//Fast return when the original URL has already been restored.
            return result;
        }
        int maxId = this.getMaxId();
        //TODO: return something like "Cannot accept more URL since all tiny URL are taken up." when MAX ID is reached. 
        result = super.DEFAULT_TINY_URL_PREFIX + this.makeUpToEightBits("" + maxId);
        this.updateMaxId();
        this.getMapFromOriginalUrlToTinyUrl().put(originalUrl, result);
        this.getMapFromTinyUrlToOriginalUrl().put(result, originalUrl);
        return result;
    }
}
