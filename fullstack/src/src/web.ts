import { createServer, IncomingMessage, ServerResponse } from 'http';
import { createShortUrl, loadShortUrl } from './shortUrlServices';



const server = createServer(async (request: IncomingMessage, response: ServerResponse) => {
    let resp: string = ''
    let url: string = request.url || '';
    let code: number = 200;
    switch (url) {
        case '/favicon.ico':
            code = 404;
            break
        case '/':
            resp = 'welcome'
            break
        case '/create':
            let method = (request.method || '').toLowerCase()
            switch (method) {
                case 'post':
                    await processReq(request).then(url => {
                        try {
                            let short = createShortUrl(url)
                            resp = short
                        } catch (err: any) {
                            code = 500
                            resp = err.message
                        }
                    }).catch(e => {
                        code = 500;
                        resp = 'invalid json';
                    })
                    break
                default:
                    code = 405;
            }
            break
        default:
            try {
                let long = loadShortUrl(url.replace('/', ''))
                if (long) {
                    code = 301
                    response.statusCode = code
                    response.setHeader('Location', long)
                    return response.end()
                }
            } catch (e: any) {
                code = 500;
                resp = e.message
            }


    }
    response.statusCode = code;
    response.end(resp);
}).on("error", (err) => {
    logError(err)
});

function logError(err: Error) {
    console.log(err.message)
}

interface IncomingUrlMsg {
    url: string
    code: number
    msg: string
}


function processReq(request: IncomingMessage): Promise<string> {
    return new Promise((resolve, reject) => {
        let body: string = '';
        request.on('data', (data) => {
            body += data.toString();
        }).on('end', () => {
            try {
                let obj = JSON.parse(body)
                resolve(obj.url)
            } catch (e) {
                reject(e)
            }
        })
    })
}

export {
    server,
    processReq,
}