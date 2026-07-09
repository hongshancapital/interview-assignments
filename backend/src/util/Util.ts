import shortid from 'shortid';
class Util {
    static defineListener(func:Function){
        return async function (...args:Array<any>) {
            try{
                await func(...args);
            }
            catch (e) {
                console.log(e);
            }
        };
    }

    static generateShortId(): string {
        return shortid.generate().substring(0, 8);
    }

    static isValidUrl(url:string):boolean {
        let pat =  /^https?:\/\/([a-zA-Z0-9]([a-zA-Z0-9\-]{0,61}[a-zA-Z0-9])?\.)+[a-zA-Z]{2,}$/;
        return  pat.test(url);
    }
}

export default Util;