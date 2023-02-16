import { IConvert } from "./interface";
import { validateUrl, isShortDomain } from "../utilites/util";

import InvalidStrategy  from "./strategy/invalidStrategy";
import LongStrategy from "./strategy/longStrategy";
import ShortStrategy from "./strategy/shortStrategy";

export default function(inputUrl: string) {
    let convertDomainHandler: IConvert;
    if (!validateUrl(inputUrl)) {
        convertDomainHandler = new InvalidStrategy(inputUrl);
    } else if (isShortDomain(inputUrl)) {
        convertDomainHandler = new ShortStrategy(inputUrl);
    } else  {
        convertDomainHandler = new LongStrategy(inputUrl);
    }
    return convertDomainHandler;
}