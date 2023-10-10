const SHORT_HOST: string = 'https://s.com/'; // 域名是无效的, 仅作为示例使用
const SHORT_PATH_LIMIT = 8; // 短 url 路径不超过 8 字符
const CODE_RADIX = 36; // 短链接 id 编码使用 36 进制

class Util {
    HTTP_CODE = {
        OK: 200,
        INVALID_PARAM: 400,
        NOT_FOUND: 404,
        ERROR: 500,
    };
    getPath(url: string): string | null {
        if (!url.startsWith(SHORT_HOST)) {
            return null;
        }
        const results: string[] = url.split(SHORT_HOST);
        const regex = /^[0-9a-z]+$/g;
        const path = results[1];
        if (!path.length || path.length > SHORT_PATH_LIMIT || !regex.test(path)) {
            return null;
        }
        return path; // 使用小写路径
    }
    calcPathToId(path: string): number {
        return parseInt(path, CODE_RADIX);
    }
    calcIdToPath(val: number): string | null {
        if (val <= 0) {
            return null;
        }
        return Number(val).toString(CODE_RADIX);
    }
    buildShortUrl(path: string): string {
        return SHORT_HOST + path;
    }
}

export default new Util();
