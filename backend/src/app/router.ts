import express, { Express, Request, Response, NextFunction } from 'express';
import ShortLinkController from './controller';

const router = express.Router();


router.post('/add', async (req: Request, res: Response) => {
    const originLink: string = req.body.originLink;
    const inputKey: string = req.body.key;

    if (!originLink || originLink === "") {
        res.send({
            error: "originLink is empty"
        })
        return
    }

    if (originLink.length > 8182) {
        res.send({
            error: "originLink is too long"
        })
        return
    }

    if (inputKey && inputKey.length > ShortLinkController.KeyLength) {
        res.send({
            error: "key is too long"
        })
        return
    }

    const shortLinkController = new ShortLinkController(inputKey, originLink)
    const {key, error} = await shortLinkController.add()
    res.send({
        key,
        error
    });
})

router.get('/:key', async (req: Request, res: Response) => {
    const controller = new ShortLinkController(req.params.key)
    const {originLink, error} = await controller.get();
    if (!originLink || originLink === "") {
        res.send({
            "error": error,
            "originLink": null,
        })
        return
    }

    res.redirect(301, originLink)
})

export default router;