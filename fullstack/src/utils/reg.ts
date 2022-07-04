const urlReg = new RegExp(
  /^(https?:\/\/(([a-zA-Z0-9]+-?)+[a-zA-Z0-9]+\.)+[a-zA-Z]+)(:\d+)?(\/.*)?(\?.*)?(#.*)?$/,
);
export function isValidUrl(url: string): boolean {
  return urlReg.test(url);
}
