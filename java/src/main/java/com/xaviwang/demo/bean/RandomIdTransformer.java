package com.xaviwang.demo.bean;

import java.util.Random;

public class RandomIdTransformer extends Transformer {

	/**
	 * Object random helps on generate random ID.
	 */
    private Random random;
    /**
     * Object MAX_ID is to define the maximum of random's bound. It's final object to avoid unexpected code change.
     */
    private final int MAX_ID = 99999999;

    public RandomIdTransformer() {
        this.random = new Random();
    }
    
    /**
     * Function transformFromOriginalUrlToTinyUrl provides the implementation of transforming original URL to Tiny URL in random way, this method will override the one in parent class Transformer.
     * @param originalUrl, the original URL customer provides. 
     * @return result which is tiny URL being calculated according to the incremental algorithm.
     */
    @Override
    public String transformFromOriginalUrlToTinyUrl(String originalUrl) {
        String result = this.mapFromOriginalUrlToTinyUrl.get(originalUrl);
        if (result != null) {
            return result;
        }
        do {
        	//Exit when find out a valid random ID
            int randomId = this.random.nextInt(MAX_ID);
            result = super.DEFAULT_TINY_URL_PREFIX + this.makeUpToEightBits("" + randomId);
        } while (this.getMapFromTinyUrlToOriginalUrl().get(result) != null);
        this.getMapFromOriginalUrlToTinyUrl().put(originalUrl, result);
        this.getMapFromTinyUrlToOriginalUrl().put(result, originalUrl);
        return result;
    }

}