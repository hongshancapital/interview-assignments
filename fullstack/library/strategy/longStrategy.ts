import { IConvert, urlHanlder } from '../interface';
import DBConnect from '../dbConect';
import { SHORT_LENGTH, BASE_URL } from '../../utilites/constant';

export default class LongStrategy extends DBConnect implements IConvert {
    longUrl: string;
    constructor(inputUrl: string) {
        super();
        this.longUrl = inputUrl
    }
    convert(handleUrl: urlHanlder) {
        this.findShortDomain(this.longUrl, (err, shortUrl) => {
            if (shortUrl) {
                handleUrl(err, shortUrl);
            } else {
                console.log('---2---')
                const urlId = require('nanoid')(SHORT_LENGTH);
                const shortDomainUrl = `${BASE_URL}/${urlId}`;
                this.insertDatabase(this.longUrl, shortDomainUrl, function(err, data) {
                    handleUrl(err, data);
                })
            }
        });
    };
}