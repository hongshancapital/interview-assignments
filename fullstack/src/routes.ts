import { ShorterController } from "./controller/ShorterController"

// restful likes rails parttern.
export const Routes = [ {
    method: "get",
    route: "/shorter/:shorter",
    controller: ShorterController,
    action: "findByShorter"
}, {
    method: "post",
    route: "/shorter",
    controller: ShorterController,
    action: "save"
}]