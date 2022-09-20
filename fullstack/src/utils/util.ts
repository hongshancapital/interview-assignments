import * as _ from 'lodash';
import { v4 as uuidv4 } from 'uuid';
import { decode, encode } from 'js-base64';

class Util {
  type(obj: any): string {
    return Object.prototype.toString.call(obj)
  }

  isObject(obj: any): boolean {
    return this.type(obj) === '[object Object]'
  }

  isArray(obj: any): boolean {
    return this.type(obj) === '[object Array]'
  }

  isString(obj: any): boolean {
    return this.type(obj) === '[object String]'
  }

  isNumber(obj: any): boolean {
    return this.type(obj) === '[object Number]'
  }

  isDate(obj: any): boolean {
    return this.type(obj) === '[object Date]'
  }

  isFunction(obj: any): boolean {
    return this.type(obj) === '[object Function]'
  }
  
  toCamelObj(obj: object): object {
    const result = {}
    for (const key in obj) {
      if (_.isFunction(obj[key])) continue
      result[_.camelCase(key)] = obj[key]
    }
    return result
  }
  toUnderLine(str: string): string{
    const matchArr = str.match(/[A-Z]/g) || []
    matchArr.forEach(item => {
      str = str.replace(item, '_' + item.toLowerCase())
    })
    return str
  }
  toUnderLineObj(obj: object): object {
    const result = {}
    for (const key in obj) {
      if (_.isFunction(obj[key])) continue
      result[this.toUnderLine(key)] = obj[key]
    }
    return result
  }
  uuid (): string {
    return uuidv4()
  }
  getType (target: any): string {
    const type = Object.prototype.toString.call(target) as string
    return type.split(/\s/)[1].replace(']', '').toLowerCase()
  }
  toDoubleNumber (num: string|number): string {
    const v = String(num)
    return num > 9 ? v : '0' + v
  }
  formatDate (date: Date = new Date(), formatter = 'yyyy-mm-dd'): string {
    return formatter
      .replace('yyyy', String(date.getFullYear()))
      .replace('mm', this.toDoubleNumber(date.getMonth() + 1))
      .replace('dd', this.toDoubleNumber(date.getDate()))
  }
  /**
   * 日期格式化
   * @param date
   * @param formatter
   * @returns {string}
   */
  timeFormat (date: Date | number = new Date(), formatter = 'hh:mm:ss'): string {
    if (_.isDate(date)) {
      return formatter
        .replace('hh', this.toDoubleNumber(date.getHours()))
        .replace('mm', this.toDoubleNumber(date.getMinutes()))
        .replace('ss', this.toDoubleNumber(date.getSeconds()))
    } else if (_.isNumber(date)) {
      // formatter 格式化规则 如:{s:'秒', h:'小时', m: '分钟'}
      const fmt = _.isObject(formatter) ? formatter : {}
      let s = parseInt((parseFloat(String(date)) / 1000).toFixed(0))
      let m = parseInt(String(s / 60))
      const h = parseInt(String(m / 60))
      s = s - m * 60
      m = m - h * 60
      let format = {
        s: 's',
        m: 'm',
        h: 'h'
      }
      format = _.assign(format, fmt || {})
      let text = s + format.s
      text = (m ? m + format.m : '') + text
      text = (h ? h + format.h : '') + text
      return text
    }
  }
  getRelativeDate (history: number): Date {
    const today = new Date()
    return new Date(today.getTime() - 24 * 60 * 60 * 1000 * history)
  }
  getDomain (url: string): string {
    const arr = url.match(/\/\/\S*?\//);
    if(arr && arr.length){
      return arr[0].replace(/\//g, '');
    }
    return '';
  }
  async delay (time: number): Promise<void> {
    return new Promise((resolve) => {
      setTimeout(() => {
        resolve()
      }, time)
    })
  }
  webToPwd (pwd: string): string {
    let res = ''
    if (!pwd) return res
    const webRc4 = decode(pwd.replace(/-/g, '+'))
    res = this.getEncPwd(this.getRc4String(webRc4, '0&Gqs0a5'))
    return res
  }
  getEncPwd (input: string): string {
    let res = ''
    if (!input) return res
    res = encode(this.getRc4String(input, 'MRaRv3TjBaJn0F3m'))
    return res
  }
  getRc4String (rc4: string, key: string): string {
    const is = []
    const ik = []
    for (let i = 0; i < 256; i++) {
      is.push(i)
      ik.push(key.charAt((i % key.length)).charCodeAt(0))
    }
    let j = 0
    for (let i = 0; i < 255; i++) {
      j = (j + is[i] + ik[i]) % 256
      const temp = is[i]
      is[i] = is[j]
      is[j] = temp
    }
    const iInputChar = []
    for (let o = 0; o < rc4.length; o++) {
      iInputChar[o] = rc4.charAt(o)
    }
    const iOutputChar = []
    j = 0
    let i = 0
    for (let x = 0; x < iInputChar.length; x++) {
      i = (i + 1) % 256
      j = (j + is[i]) % 256
      const temp = is[i]
      is[i] = is[j]
      is[j] = temp
      const t = (is[i] + (is[j] % 256)) % 256
      const iY = is[t]
      iOutputChar[x] = String.fromCharCode(iInputChar[x].charCodeAt(0) ^ iY)
    }
    return iOutputChar.join('')
  }

  /**
   * 合并对象
   * @param defaults
   * @param extend
   * @returns {*}
   */
   mergeObject<T>(defaults: T, extend: T): T {
    for (const key in defaults) {
      const defaultsProp = defaults[key]
      const extendProp = extend[key]
      if (this.isValid(extendProp)) {
        extend[key] = this.clone(defaultsProp)
      } else if (_.isObject(defaultsProp)) {
        this.mergeObject(defaultsProp, extendProp)
      } else if (_.isArray(defaultsProp)) {
        if (_.isArray(extendProp)) {
          for (
            let i = 0;
            i < defaultsProp.length && i < extendProp.length;
            i++
          ) {
            extendProp[i] = this.mergeObject(defaultsProp[i], extendProp[i])
          }
        } else {
          extend[key] = defaultsProp
        }
      } else {
        extend[key] = this.isValid(extendProp) ? defaultsProp : extendProp
      }
    }
    return extend
  }

  /**
   * 判断对象是否是null或undefined
   * @param obj
   * @returns {boolean}
   */
   isValid(obj: any): boolean {
    return obj === null || obj === undefined
  }

  clone(obj: any): any {
    if (this.isObject(obj)) {
      const result: {
        [propName: string]: any;
      } = {}
      for (const key in obj) {
        const prop = obj[key]
        result[key] = this.clone(prop)
      }
      return result as any
    } else if (this.isArray(obj)) {
      const result = []
      for (let i = 0; i < (obj as any[]).length; i++) {
        const item = obj[i]
        result.push(this.clone(item))
      }
      return result
    } else {
      return obj
    }
  }
}
export default new Util()