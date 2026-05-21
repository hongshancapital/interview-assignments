import { expect } from 'chai';

import * as storage from 'storage/url_storage'

describe("test url storage", function() {
    it("get or insert id from long url", function(){ 
        const longUrl = "http://test.url";
        const id1 = storage.getOrInsertFromLongUrl(longUrl);
        const id2 = storage.getOrInsertFromLongUrl(longUrl);
        expect(id1).to.equal(id2);
    });


    it("get long url from id", function(){ 
        const longUrl = "http://test.url";
        const id = storage.getOrInsertFromLongUrl(longUrl);
        const expectedLongUrl = storage.getLongUrlFromId(id);
        expect(expectedLongUrl).to.equal(longUrl);
    });

    it("has id", function(){ 
        const longUrl = "http://test.url";
        const id = storage.getOrInsertFromLongUrl(longUrl);
        const hasId = storage.hasId(id);
        expect(hasId).to.be.true;
    });

});
