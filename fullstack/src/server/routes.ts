import {type Express, Request, Response, NextFunction} from "express";
import {createShortUrl, getLongUrl, isValidUrl} from "./services/shorturl";

export function bindRoutes(app: Express){
    app.get("/go/:shortUrl", async (req, res) => {
        const { shortUrl } = req.params;
        if (shortUrl) {
            const longUrl = await getLongUrl(shortUrl);
            if (longUrl !== null) {
                return res.redirect(301, longUrl);
            }
        }

        res.status(404).send("This page does not exist");
    });

    app.post("/api/create", async (req, res, next) => {
        const { longUrl } = req.body;
        if (!isValidUrl(longUrl)) {
            return res.status(400).json({
                message: "Invalid url format",
            });
        }
        try {
            const shortUrl = await createShortUrl(longUrl);
            res.status(201).json({
                shortUrl,
            });
        } catch (e) {
            next(e);
        }
    });
}

export function defaultErrorHandler(err: Error, req: Request, res: Response, next: NextFunction) {
    console.error("DefaultErrorHandler", err);

    if (res.headersSent) {
        return next(err);
    }

    res.status(500).json({ error: err, message: "Server Error" });
}

