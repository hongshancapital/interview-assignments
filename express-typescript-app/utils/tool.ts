/**
 * tool.js
 * @authors lizilong
 * @date    2022-05-10
 * @description 工具箱
 */

// @ts-ignore
import ids from 'short-id'

/**
 *  短域名生成器
 */
const shortUrlGenerator = () => {
	// 保证短域名长度最大为 8 个字符，长度可控暂定为 8 个字符
	ids.configure({
		length: 8, // 生成长度
		algorithm: 'sha1', // 用于生成的哈希算法
		salt: Math.random, // 盐值
	})
	return ids.generate()
}

/**
 *  判断Url是否合法
 */
const checkUrl = (url: string) => {
  let exp = new RegExp(/http(s)?:\/\/([\w-]+\.)+[\w-]+(\/[\w- .\/?%&=]*)?/);
  return exp.test(url) === true ? true : false
} 

const tool = {
  shortUrlGenerator,
  checkUrl
}
export default tool
