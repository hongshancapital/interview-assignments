import { getRepository } from "typeorm"
import { NextFunction, Request, Response } from "express"
import * as URL from "url";
import * as validUrl  from "valid-url"
import * as shortid from "shortid";
import { Shorter } from "../entity/Shorter"

export class ShorterController {

    private shorterRepository = getRepository(Shorter)

    async findByShorter(request: Request, response: Response) {
        const shorter = request.params.shorter
        const data = await this.shorterRepository.findOne({
            where: {
                shorter
            }
        })
        if (!data) {
            return response.status(404).json('Invalid Shorter')
        }
        return {
            ...data, link: [data.origin, data.path].join('')
        }
    }

    async save(request: Request, response: Response) {
        const link = request.body.link
        if (!validUrl.isUri(link)) {
            return response.status(401).json('Invalid URL: please check it again.')
        }
        const { pathname: path, origin } = new URL(link)
        const data = await this.shorterRepository.findOne({ // check exists?
            where: { origin, path }
        })
        if (data) {
            return data
        }
        // otherwise save shorter model.
        const shorter = shortid.generate()
        /** 关于 shortid duplicate 的回复 @2022.06.07 by eten
         *  shortid readme 声称：Can generate any number of ids without duplicates, even millions per day.
         *  这也是选择三方库的一个原因，鉴于面试场景单纯的三方库使用不足以满足应试需求，故简单描述 shortid 实现原理
         *  shortid 底层依赖 nanoid，nanoid 本身依赖 node.js 的 crypto 模块，采用硬件随机生成器，而不是传统随机数声称方式 Math.random()，因此更好的保证了随机数不可预测。
         *  源码方面
         *      1. 生成一个随机的最大 255 位的 Buffer，然后对每位执行&63操作，相当于把0-255映射到0-63
         *      2. 在 1 基础上进一步封装，通过 Math.clz32 位运算生成一个标记 mask
         *      3. 对字符长度做线性计算，生成一个步长 step，这里据说采用系数 1.6 是经过大量实验验证的稳定的参数
         *      4. 循环步长内字节，对 mask 做与运算，返回，这里如果想固定输出字符长度可以透传 step 参数重写 步骤3 的 step
         * 
         *  性能方面据说 nanodid 要优于 uuidV4 60%，碰撞检测实验方面据说 nanodid 和 uuidV4 相当，** 所以其重复概率非常低 **
         * 
         *  至于如果真的发生重复该如何做，有两种方式，一种是前置校验是否重复，另一种是效仿“任其崩溃”的设计原则直接抛出异常。
         *  这里我偏向采用后者，因为为了几乎零概率事件做没必要的库读操作会拉低程序性能，甚至高并发情况下导致请求超时，得不偿失。
         *  因此我在实体定义限制了 shorter 字段为 unique，保证程序写操作重复时抛出正确异常信息，方便问题排查，而针对用户测反馈则保留为保存失败（因为用户无需关心和消化计算机底层问题）。
         */
        try {
            const data = await this.shorterRepository.save({
                origin, // eg: https://eten.wang
                shorter, // eg: abcd12
                path // eg: /blogs
            })
            return [origin, shorter].join('')
        } catch (error) {
            return response.status(501).json('Internal Error:' + error.toString())
        }
  
    }

}