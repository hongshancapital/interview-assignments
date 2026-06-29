import {Request, Response} from 'express';

export default (req: Request, res: Response, next: (param?: unknown) => void): void => {
    console.clear()
    const date: Date = new Date();
    console.table([{
        date: `${date.toLocaleDateString()} - ${date.toLocaleTimeString()}`,
        method: req.method,
        url: `${req.baseUrl}${req.url}`,
        body: req.body
    }])
    next();
}
