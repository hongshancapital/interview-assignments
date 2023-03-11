import { expect } from 'chai';
import sinon from 'sinon'

import * as service from 'services/shortener_service'
import * as storage from 'storage/url_storage'

describe("test shortner service", function() {
    it("shorten url base case", function(){
        const getLongUrl = 
            sinon
            .stub(storage, "getOrInsertFromLongUrl")
            .returns(1234567);
        const shortUrl = service.shortenUrl("http://test.url");
        expect(getLongUrl.calledOnce).to.be.true;
        expect(shortUrl).to.equal("http://go/00004Jq7");
    });

    it("shorten url with id equals to 0", function(){
        const getLongUrl = 
            sinon
            .stub(storage, "getOrInsertFromLongUrl")
            .returns(0);
        const shortUrl = service.shortenUrl("http://test.url");
        expect(getLongUrl.calledOnce).to.be.true;
        expect(shortUrl).to.equal("http://go/00000000");
    });

    it("get long url for unknow short url throws error", function(){
        const shortUrl = "http://go/unknownu";
        const hasId = 
            sinon.stub(storage, "hasId")
            .returns(false);
        expect(()=>service.getLongUrl(shortUrl)).to.throw();
        expect(hasId.calledOnce).to.be.true;
    });

    it("get long url for existed short url", function(){
        const shortUrl = "http://go/validurl";
        const longUrl = "http://longurl.com";
        const hasId = 
            sinon.stub(storage, "hasId")
            .returns(true);
        const getLongUrlFromId = 
            sinon
            .stub(storage, "getLongUrlFromId")
            .returns(longUrl);
        const expectedLongUrl = service.getLongUrl(shortUrl);

        expect(hasId.calledOnce).to.be.true;
        expect(getLongUrlFromId.calledOnce).to.be.true;
        expect(expectedLongUrl).to.equal(longUrl);
    });

    afterEach(() => {
        sinon.restore();
    });
})
