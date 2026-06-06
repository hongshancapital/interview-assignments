// 将十进制发号器里的数字转化成 [0-9, a-z, A-Z] base62
export function url2Base62(seqNum: number): string {
    // 为防止碰撞破解，这里62进制的字符打乱顺序，但只能增加碰撞的难度（所以这里的根本解决方法？）
    const char62 =
        "abcABCdefghDEFGHIJKLijklMNOPQRSmnTUVopqrstuvwxyzWXYZ0123456789"
    const radix = char62.length
    let decimalNum = seqNum
    let arr = []
    do {
        const mod = decimalNum % radix
        decimalNum = (decimalNum - mod) / radix
        arr.unshift(char62[mod])
    } while (decimalNum)
    return arr.join("")
}
