/// <reference types="vite/client" />

import { Connection } from "mongoose";

declare global {
    namespace NodeJS {
        interface Global {
            mongoose: Connection
        }
    }
}

declare module "nanoid" {

}


export default global
