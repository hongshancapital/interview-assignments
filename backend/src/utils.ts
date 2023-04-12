const REGEX_HTTP_PROTOCOL = /^https?:\/\//i

export const isValidHttpUrl = (url:string):boolean =>{
    try{
        const {href} = new URL(url)  
        return REGEX_HTTP_PROTOCOL.test(href) && !!href 
    }catch(err){
        return false
    }
}