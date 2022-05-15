
const BASE62_CHARACTERS = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'
const BASE = 62

export const ToBase62Str = (id: number): string => {
  const strArr: string[] = [];
  while (id) {
    strArr.unshift(BASE62_CHARACTERS[id%BASE]);
    id = Math.floor(id/62)
  }
  return strArr.join('')
}

export const FromBase62Str = (str: string): number => {
  let id = 0;

  // 转回数字时，需要从右往左遍历
  for(let i = 0; i < str.length; i++) {

    if (str[i]>='0' && str[i]<='9') {
      id = id * 62 + Number.parseInt(str[i])
    } 
    if (str[i]>='A' && str[i]<='Z') {
      // 字母 A 的 ascii 码的值为 65，对应 62 进制的 10
      // 减去 （65-10） 就是 62 进制中的实际数值
      id = id * 62 + str.charCodeAt(i) - 55
    }
    if (str[i]>='a' && str[i]<='z') {
      // 字母 a 的 ascii 码的值为 97，对应 62 进制的 10
      // 减去 （97-36） 就是 62 进制中的实际数值
      id = id * 62 + str.charCodeAt(i) - 61
    }
  }
  return id
}