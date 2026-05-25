/**
 * 返回字符串 为n个char构成
 * @param char 重复的字符
 * @param count 次数
 * @return String
 * @author adswads@gmail.com
 */
export const charString = (char: string, count: number): string => {
  var str: string = "";
  while (count--) {
    str += char;
  }
  return str;
};

/**
 * 对日期进行格式化， 和C#大致一致 默认yyyy-MM-dd HH:mm:ss
 * 可不带参数 一个日期参数 或一个格式化参数
 * @param date 要格式化的日期
 * @param format 进行格式化的模式字符串
 *     支持的模式字母有：
 *     y:年,
 *     M:年中的月份(1-12),
 *     d:月份中的天(1-31),
 *     H:小时(0-23),
 *     h:小时(0-11),
 *     m:分(0-59),
 *     s:秒(0-59),
 *     f:毫秒(0-999),
 *     q:季度(1-4)
 * @return String
 * @author adswads@gmail.com
 */
export const dateFormat = (date?: any, format?: string): string => {
  //无参数
  if (date == undefined && format == undefined) {
    date = new Date();
    format = "yyyy-MM-dd HH:mm:ss";
  }
  //无日期
  else if (typeof date == "string") {
    format = date;
    date = new Date();
  }
  //无格式化参数
  else if (format === undefined) {
    format = "yyyy-MM-dd HH:mm:ss";
  } else {
  }
  //没有分隔符的特殊处理

  let map: any = {
    y: date.getFullYear() + "", //年份
    M: date.getMonth() + 1 + "", //月份
    d: date.getDate() + "", //日
    h: date.getHours(), //小时 12
    H: date.getHours(), //小时 24
    m: date.getMinutes() + "", //分
    s: date.getSeconds() + "", //秒
    q: Math.floor((date.getMonth() + 3) / 3) + "", //季度
    f: date.getMilliseconds() + "", //毫秒
  };
  //小时 12
  if (map["H"] > 12) {
    map["h"] = map["H"] - 12 + "";
  } else {
    map["h"] = map["H"] + "";
  }
  map["H"] += "";

  let reg = "yMdHhmsqf";
  let all = "",
    str = "";
  for (let i = 0, n = 0; i < reg.length; i++) {
    n = format.indexOf(reg[i]);
    if (n < 0) {
      continue;
    }
    all = "";
    for (; n < format.length; n++) {
      if (format[n] != reg[i]) {
        break;
      }
      all += reg[i];
    }
    if (all.length > 0) {
      if (all.length == map[reg[i]].length) {
        str = map[reg[i]];
      } else if (all.length > map[reg[i]].length) {
        if (reg[i] == "f") {
          str = map[reg[i]] + charString("0", all.length - map[reg[i]].length);
        } else {
          str = charString("0", all.length - map[reg[i]].length) + map[reg[i]];
        }
      } else {
        switch (reg[i]) {
          case "y":
            str = map[reg[i]].substr(map[reg[i]].length - all.length);
            break;
          case "f":
            str = map[reg[i]].substr(0, all.length);
            break;
          default:
            str = map[reg[i]];
            break;
        }
      }
      format = format.replace(all, str);
    }
  }
  return format;
};

// 带_字符串转驼峰
export function ToHump(name: string): string {
  if (name.includes("_")) {
    let oarr = name.split("_");
    for (let i = 1; i < oarr.length; i++) {
      oarr[i] = oarr[i].charAt(0).toUpperCase() + oarr[i].slice(1);
    }
    return oarr.join("");
  }
  return name;
}

// 对象属性带_转驼峰
export function HumpObj(obj: any) {
  if (!obj) return obj;

  let humpObj: any = {};
  for (let key in obj) {
    humpObj[ToHump(key)] = obj[key];

    if (obj[key] && obj[key] instanceof Object) {
      humpObj[ToHump(key)] = HumpObj(obj[key]);
    }
  }

  return humpObj;
}

// 数组对象转驼峰
export function HumpArray(arr: any) {
  if (arr instanceof Array) {
    arr.forEach((item, i) => {
      arr[i] = HumpObj(item);
    });
  } else {
    arr = HumpObj(arr);
  }
  return arr;
}

// 渲染二级菜单
export function renderMenu(arr: any, topValue: any = 0) {
  let newArray: any[] = [];
  // 添加一级菜单
  for (let i = 0; i < arr.length; i++) {
    if (arr[i]["parentId"] == topValue) {
      arr[i].list = [];
      newArray.push(arr[i]);
    }
  }
  for (let i = 0; i < arr.length; i++) {
    for (let j = 0; j < newArray.length; j++) {
      if (arr[i]["parentId"] == newArray[j]["menuId"]) {
        newArray[j].list.push(arr[i]);
      }
    }
  }
  return newArray;
}

// 获得当前时间
export function getCurrentTime(): string {
  let ctime = dateFormat(new Date(), "yyyy-MM-dd HH:mm:ss");
  return ctime;
}

// 字符串转16进制
export function toHex(str: string): string {
  let val = "";
  for (let i = 0; i < str.length; i++) {
    if (val == "") val = str.charCodeAt(i).toString(16);
    else val += str.charCodeAt(i).toString(16);
  }
  val += "0a";
  return val;
}

// 数字转62进制
export function decimalTo62(num: number): string {
  const base62 =
    "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
  let res = "";
  while (num > 0) {
    const remainder = num % 62;
    res = base62.charAt(remainder) + res;
    num = Math.floor(num / 62);
  }
  return res;
}

/**
 * 生成一个唯一的字符串作为短域名
 * @returns string 短域名
 */
export function getShortUrl(): string {
  // 使用当前时间戳+随机数作为唯一id，随机数取10位，避免id重复
  const timestamp = new Date().getTime();
  const rand = Math.floor(Math.random() * Math.pow(10, 10));
  // 转62进制返回字符串
  return decimalTo62(timestamp + rand);
}
