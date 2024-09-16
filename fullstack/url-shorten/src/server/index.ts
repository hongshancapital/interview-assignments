import express from "express"

export default class Server {
    private _app: express.Application
    private port: number

    constructor(port: number) {
        this.port = port
        this._app = express()
    }

    public get app(): express.Application {
        return this._app
    }

    static init(port: number): Server {
        return new Server(port)
    }

    public start(): void {
        this._app.listen(this.port)
    }
}
