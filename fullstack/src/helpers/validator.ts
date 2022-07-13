// 校验url的合法性
export function validateUrl(url: string): boolean {
  const urlReg = new RegExp(
    /^(https?:\/\/(([a-zA-Z0-9]+-?)+[a-zA-Z0-9]+\.)+[a-zA-Z]+)(:\d+)?(\/.*)?(\?.*)?(#.*)?$/,
  );
  return urlReg.test(url);
}

//校验key的合法性
export function validateKey(key: string): boolean {
  const keyReg = new RegExp(
   /^[A-Za-z0-9]{8}$/
  );
  return keyReg.test(key);
}