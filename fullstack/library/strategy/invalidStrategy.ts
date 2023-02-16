import { IConvert, urlHanlder } from '../interface';
import DBConnect from '../dbConect';

export default class InvalidStrategy extends DBConnect implements IConvert {
    inputUrl: string;
    constructor(inputUrl: string) {
        super();
        this.inputUrl = 'Invalid Url';
    }
    convert(handleUrl: urlHanlder) {
        handleUrl(null, this.inputUrl);
    };
}