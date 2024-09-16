function checkLongUrl(url:string){
    if(!url){return false}
    else{
        let urlReg = /(http|ftp|https):\/\/[\w\-_]+(\.[\w\-_]+)+([\w\-\.,@?^=%&amp;:/~\+#]*[\w\-\@?^=%&amp;/~\+#])?/;
        return urlReg.test(url)
    }
   
}
function getrandomIndex(min:number,max: number,i:number){
    let index = Math.floor(Math.random()*(max-min+1)+min);
    return index;
}

function getShortID(len){
    let _sym = 'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890',
    str = '';
    let length = len || 8;
    for(let i =0 ;i<length;i++){
        let sindex = getrandomIndex(0,_sym.length-1,i);
        str+=_sym[sindex];
    }
    return str;
}
export default {checkLongUrl,getShortID};