import os from 'os';
import Hashids from 'hashids';
import { Transaction } from 'sequelize';
import { Request, Response } from 'express';
import db from '../models';
import { getBytes } from '../utils/string';

let status = 'pause';
const queue: (() => Promise<void>)[] = [];
const LinkModel = db.link;
const shortLinkReg = /^[a-zA-Z0-9]{1,8}$/;
const orginLinkReg = /(http|ftp|https):\/\/[\w\-_]+(.[\w\-_]+)+([\w\-.,@?^=%&:/~+#]*[\w\-@?^=%&/~+#])?/;
/**
 * url limit to explorer
 * 1、IE 2048
 * 2、360 2118
 * 3、Firefox 65536
 * 4、Safari 80000
 * 5、Opera 190000
 * 6、Chrome 8182
 */

// 自增序列算法可能更好一些，如果只有一个 master 专用写更好
const linkGenerator = (id: number) => {
  const hashid = new Hashids(os.hostname());
  let hash = hashid.encode(id);
  if (hash.length > 8) {
    hash = hash.substr(0, 8);
  }
  return hash;
}

export const create = async (req: Request, res: Response) => {
  let { link } = req.body;

  if (Object.prototype.toString.call(link) !== '[object String]') {
    res.json({
      message: "Params(link) should be a string!",
      code: 400
    });
    return;
  }

  link = link.trim();
  if (!link) {
    res.json({
      message: "Params(link) can not be empty!",
      code: 400
    });
    return;
  }

  if (!orginLinkReg.test(link)) {
    res.json({
      message: "Params(link) not valid! Please check your input.",
      code: 400
    });
    return;
  }

  const bytes = getBytes(link);
  if (bytes > 2118) {
    res.json({
      message: "Params link is too long! The limit bytes is 2118.",
      code: 414
    });
    return;
  }

  try {
    /** 使用事务锁避免并发写了同一个 id
     * READ_UNCOMMITTED 读未提交
     * READ_COMMITTED 读已提交
     * REPEATABLE_READ 可重复读
     * SERIALIZABLE 可串行化
    */
    queue.push(async () => {
      await db.sequelize.transaction({
        autocommit: true,
        isolationLevel: Transaction.ISOLATION_LEVELS.SERIALIZABLE
      }, async (transaction: any) => {
        const total = await LinkModel.count({ transaction });
        const shortLink = linkGenerator(total + 1);
      
        const insertData = {
          origin: link,
          short: shortLink
        };
  
        await LinkModel.create(insertData, { transaction });

        if (queue.length) {
          const [fun] = queue.splice(0, 1);
          fun();
        } else {
          status = 'pause';
        }
  
        res.json({
          message: 'success',
          code: 0,
          data: {
            ...insertData,
            domain: 'http://localhost'
          }
        });
      })
    });

    if (status === 'pause' && queue.length) {
      status = 'running';
      const [fun] = queue.splice(0, 1);
      fun();
    }
  } catch (err: any) {
    // 如果是因为 shortlink 重复导致的创建失败，则证明算法需要改进
    // todu: reportError
    res.send({
      message:
        err.message || "Service not avaliable. Please contact the administrator!",
      code: 500
    });
  }
};

export const find = async (req: Request, res: Response) => {
  const { key } = req.query;
  let param = key ? String(key) : '';
  param = param.trim();

  if (!param) {
    res.json({
      message: "Params(key) can not be empty!",
      code: 400
    });
    return;
  }

  if (param.includes('//')) {
    const url = new URL(param);
    param = url.pathname.split('/')[1];
  }
  
  if (!shortLinkReg.test(param)) {
    res.send({
      message: "Short link path not valid. Please check your input!",
      code: 400
    });
    return;
  }

  const dataSet = await LinkModel.findOne({ where: { short: param } });
  if (dataSet) {
    res.json({
      message: 'success',
      code: 0,
      data: (dataSet as any).origin
    });
  } else {
    res.json({
      message: 'No origin url found. Please check the input.',
      code: 404
    });
  }
};
