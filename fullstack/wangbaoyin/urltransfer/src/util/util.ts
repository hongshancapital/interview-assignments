/***
 * 返回62进制字符串
 * @param num
 * @return string
 */
 function num10to62(num:string): string{
    const chars ='0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ'.split('');
    const radix = chars.length;
    if(isNaN(+num)){
      return "";
    }
    let qutient = +num;
    const arr = [];
    
    do {
    const mod = qutient % radix;
    qutient = (qutient - mod) / radix;
    arr.unshift(chars[mod]);
  } while (qutient);
 
  return arr.join('');
  
}
/***
 * 
 * 检测URL有效性
 * @param str
 * @return string
 */
function validURL(str: string):boolean {
  if(str === null || str === undefined){
    return false;
}
  const pattern = new RegExp(
    '^(https?:\\/\\/)?' + // protocol
    '((([a-z\\d]([a-z\\d-]*[a-z\\d])*)\\.)+[a-z]{2,}|' + // domain name
    '((\\d{1,3}\\.){3}\\d{1,3}))' + // OR ip (v4) address
    '(\\:\\d+)?(\\/[-a-z\\d%_.~+]*)*' + // port and path
    '(\\?[;&a-z\\d%_.~+=-]*)?' + // query string
      '(\\#[-a-z\\d_]*)?$',
    'i',
  ); 

  return pattern.test(str);
}

export { num10to62, validURL };;