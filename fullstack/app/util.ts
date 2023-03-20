import fs from 'fs';

// 用当前时间生成一个唯一七位数的uuid
export function createSid() {
  const uuid = Date.parse(new Date() as any)/1000;
  return uuid.toString(32);
}

// 是否有效url
export function checkUrl(url: string) {
  const urlReg = /^((https?|ftp|file):\/\/)?([\da-z\.-]+)\.([a-z\.]{2,6})([\/\w \.-]*)*\/?$/;
  return urlReg.test(url)
}

// 封装自动以返回内容
export function customRes(code: number, msg: string, body: string) {
  return {
    code, msg, body
  }
}

export function getItemBySid(sid: string) {
  try {
    const content = fs.readFileSync('./app/db.json', 'utf8');
    if (!content) {
      return false;
    } else {
      const data = JSON.parse(content);
      if(data.length === 0) return false;
      for (let item of data) {
        if (item.sid === sid) return item;
      }
      return false;
    }
  } catch (error) {
    
  }
}

export function getItemByLongLink(url: string) {
  try {
    const content = fs.readFileSync('./app/db.json', 'utf8');
    if (!content) {
      return false;
    } else {
      const data = JSON.parse(content);
      if(data.length === 0) return false;
      for (let item of data) {
        if (item.url === url) return item;
      }
      return false;
    }
  } catch (error) {
    
  }

}

export function addItem(item: {sid: string, url: string}) {
  const { sid, url} = item;
  if(!sid || !url) {
    throw Error('sid or url required');
  }

  try {
    const content = fs.readFileSync('./app/db.json', 'utf8');
    console.log('content', content);
    let data = !content ? [] : JSON.parse(content);
    data.push(item);

    fs.writeFile('./app/db.json', JSON.stringify(data), (err: any) => {
      if (err) {
        // 异常处理
        console.log(err);
      }
    })
  } catch (error) {
    console.log(error);
  }
}