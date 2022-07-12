// 校验数据
export function validateUrl(url: string): boolean {
  const urlReg = new RegExp(
    /^(https?:\/\/(([a-zA-Z0-9]+-?)+[a-zA-Z0-9]+\.)+[a-zA-Z]+)(:\d+)?(\/.*)?(\?.*)?(#.*)?$/,
  );
  return urlReg.test(url);
}
export function validateKey(key: string): boolean {
  return key.length===8;
}