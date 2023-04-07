import express from "express";
import {long2ShortURL, short2LongURL} from './handler';

const app = express();
const SERVER_PORT = 12345;

// get short url by long urls
app.get('/long-to-short/:longURL', async (req, res) => {
    try {
        const longURL = req.params.longURL || '';

        const shortURL = await long2ShortURL(longURL);

        res.json({
            shortURL
        });
    } catch(err) {
        res.status(500);
    }
});

// get long url by short url
app.get('/short-to-long/:shortURL', async (req, res) => {
    const shortURL = req.params.shortURL || '';

    const longURL = await short2LongURL(shortURL);

    res.json({
        longURL
    });
});

app.listen(SERVER_PORT, () => {
    console.log('Hello World!');
});