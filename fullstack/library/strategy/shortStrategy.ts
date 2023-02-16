import { IConvert, urlHanlder } from '../interface';
import DBConnect from '../dbConect';

export default class ShortStrategy extends DBConnect implements IConvert {
    shortUrl: string;
    constructor(inputUrl: string) {
        super();
        this.shortUrl = inputUrl;
    }
    convert(handleUrl: urlHanlder) {
        this.findLongDomain(this.shortUrl, (err, longUrl) => {
            if (longUrl) {
                handleUrl(err, longUrl); 
            } else {
                handleUrl(null, this.shortUrl); // Directly handle the short URL while there is no long domain URL in database;
            }
        });
    };
}