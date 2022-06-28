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
            where: { path }
        })
        if (data) {
            return data
        }
        // otherwise save shorter model.
        const shorter = shortid.generate()
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