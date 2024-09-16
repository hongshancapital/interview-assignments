import { Ipath, IPathRoute } from "../domain/IPath";

function path(url: string): IPathRoute {
    const allRoutes: Ipath = {
        "/getShortLink": {
            methods: ["POST", "GET", "PUT", "DELETE"]
        },
        "/getLongLink": {
            methods: ["POST", "GET", "PUT", "DELETE"]
        }
    }
    return allRoutes[url];
}

export default path;
