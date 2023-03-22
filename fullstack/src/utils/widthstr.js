/*
  思路：
  一定要缓存，运行中逐步得到所有缓存
*/

var map = {}

/*
  numstr，数字字符串
  length，期望总长度
*/
module.exports = function (numstr, length) {
  var r = numstr
  if (r.length < length) {
    const delta = length - r.length
    if (!map[delta]) {
      map[delta] = ''
      for (var i = 0; i < delta; ++i) {
        map[delta] += '0'
      }
    }
    r = map[delta] + r
  } else if (r.length > length) {
    r = r.substring(r.length - length)
  }

  return r

}
