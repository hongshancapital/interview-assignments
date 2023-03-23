package com.xaviwang.demo.bean;

public class SixtyTwoBitsIdTransformer extends Transformer {

    private final String CHARS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final long MAX_LONG_ID = 218340105584895L;
    public SixtyTwoBitsIdTransformer() {
    	
    }

	/**
     * Function transformFromOriginalUrlToTinyUrl provides the implementation of transforming original URL to Tiny URL, this method will override the one in parent class Transformer.
     * @param originalUrl, the original URL customer provides. 
     * @return result which is tiny URL being calculated according to the incremental in 62 bits' algorithm.
     */
    @Override
    public String transformFromOriginalUrlToTinyUrl(String originalUrl) {
        String result = this.mapFromOriginalUrlToTinyUrl.get(originalUrl);
        if (result != null) {
        	//Fast return when the original URL has already been restored.
            return result;
        }
        long maxLongId = this.getMaxLongId();
        //TODO: return something like "Cannot accept more URL since all tiny URL are taken up." when MAX LONG ID is reached. 
        this.updateMaxLongId();
        result = super.DEFAULT_TINY_URL_PREFIX + this.makeUpToEightBits(this.transformOriginalUrlToSixtyTwoBitsString(maxLongId));
        this.getMapFromOriginalUrlToTinyUrl().put(originalUrl, result);
        this.getMapFromTinyUrlToOriginalUrl().put(result, originalUrl);
        return result;
    }
    
    /**
     * Function transformOriginalUrlToSixtyTwoBitsString helps on conversion from original URL to tiny URL with 62 bits selection.
     * @param longId, the ID in long format. 
     * @return result which is tiny URL being calculated according to the incremental in 62 bits' algorithm.
     */
    public String transformOriginalUrlToSixtyTwoBitsString(long longId) {
        StringBuilder result = new StringBuilder();
        while (longId > 0) {
        	int remainder = (int) (longId % CHARS.length());
            result.append(CHARS.charAt(remainder));
            longId /= CHARS.length();
        }
        if (result.length() == 0) {
        	result.append("0");
        }
        return result.reverse().toString();
    }
}

