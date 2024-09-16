import {Express} from 'express'
import short from "./short";

function routes(app: Express) {
    app.use('/short',short)
}

export default routes
