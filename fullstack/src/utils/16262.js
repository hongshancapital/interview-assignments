const chars = '0123456789abcdefghigklmnopqrstuvwxyzABCDEFGHIGKLMNOPQRSTUVWXYZ'.split('')
const radix = chars.length

/*
  BigNumber对象
*/
module.exports = function (ll) {
    var mod
    var arr = []
    do {
      mod = ll % radix
      ll = (ll - mod) / radix
      arr.push(chars[mod])
    } while (ll > 0)

    arr.reverse()
    return arr.join('')

}
