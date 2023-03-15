const reg = /(http|ftp|https):\/\/[\w\-_]+(\.[\w\-_]+)+([\w\-\.,@?^=%&:/~\+#]*[\w\-\@?^=%&/~\+#])?/;

// 验证url是否正确
export function isUrl(path: string) {
    return reg.test(path);
}

