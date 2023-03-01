const dictonary = "0123456789abcdefghigklmnopqrstuvwxyzABCDEFGHIGKLMNOPQRSTUVWXYZ";
const radix = dictonary.length;



class ShortUrlService{
    decimalTo62 = (number: number) => {    
        let result = "";    
        do {
            const mod = number % radix;
            number = (number - mod) / radix;
            result = dictonary[mod] + result;
        } while (number);

        return result.padStart(8, '0');    
    }

    validateUrl = (url: string) => {
        if (url.length > 2048) {
            return false;
        }
        const pattern = new RegExp(
            '^(https?:\\/\\/)' + // protocol
            '((([a-z\\d]([a-z\\d-]*[a-z\\d])*)\\.)+[a-z]{2,}|' + // domain name
            '((\\d{1,3}\\.){3}\\d{1,3}))' + // OR ip (v4) address
            '(\\:\\d+)?' + //port
            '(\\/[-a-z\\d%_.~+]*)*' + // path
            '(\\?[;&a-z\\d%_.~+=-]*)?' + // query string
            '(\\#[-a-z\\d_]*)?$', // hash
            'i',
        );
        
        return pattern.test(url);
    }
}

export default new ShortUrlService();