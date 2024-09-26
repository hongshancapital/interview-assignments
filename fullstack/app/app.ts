import express, { Express, Request, Response } from 'express';
import bodyParser from 'body-parser';
import { createSid, checkUrl, customRes, getItemByLongLink, getItemBySid ,addItem } from './util';

const app: Express = express();
app.use(bodyParser.urlencoded({ extended: false }))
app.use(bodyParser.json())

const PORT = 3001;
const BASEURL = 'http://127.0.0.1:3001';

// 输入长链，返回 or 创建短链
app.post('/short-link', async (req: Request, res: Response) => {
  let url = req.body.url;
  if (!url) {
    return res.json(customRes(1, 'url不能为空', ''));
  }
  
  if (!checkUrl(url)) {
    return res.json(customRes(2, 'url不符合规范', ''));
  }

  // 先看一下库中是否有长链
  const item = getItemByLongLink(url)
  if (item) {
    return res.json(customRes(0, '短链已存在', item.sid));
  } else {
    const sid = createSid();
    try {
      addItem({sid, url});
      return res.json(customRes(0, '短链已生成', `${BASEURL}/sid`));
    } catch (error) {
      return res.json(customRes(3, '短链生成失败', String(error)));
    }
  }
})

// 输入短链，返回长链
app.get('/long-link/:sid', async (req: Request, res: Response) => {
  const sid = req.params.sid;
  const item = getItemBySid(sid);
  if (!item) {
    return res.json(customRes(4, '没有找到该sid对应的长链', ''));
  }
  return res.redirect(item.url);
})

app.listen(PORT, () => {
  console.log(`⚡️[server]: Server is running at http://localhost:${PORT}`);
});