const REGEX_HTTP_PROTOCOL = /^https?:\/\//i

 /**
 *
 * 判断是否是合法的http（https） 协议下的url
 * @param {string} url
 * @return {*}  {boolean}
 */
 export const isValidHttpUrl = (url:string):boolean =>{
    try{
        const {href} = new URL(url)  
        return REGEX_HTTP_PROTOCOL.test(href) && !!href 
    }catch(err){
        return false
    }
}