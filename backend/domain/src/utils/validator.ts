import validator from 'validator';
export function isUrl(url: string): boolean {
  try {
    if(validator.isURL(url)) {
      return true;
    }
    return false;
  } catch (error) {
    console.error('validator error', error);
    return false;
  }
}

const codeRegex = /^[0-9a-zA-Z]{1,8}$/;
export function isCode(code: string): boolean {
  return codeRegex.test(code);
}
