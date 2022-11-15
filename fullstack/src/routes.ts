import encoder from "./controller/encoder.js"

export type Route = {
  method: string;
  route: string;
  controller: Function;
}

export const routes: Array<Route> = [{
    method: "get",
    route: "/:short",
    controller: encoder.getUrl
}, {
    method: "post",
    route: "/encode",
    controller: encoder.setUrl
}]
