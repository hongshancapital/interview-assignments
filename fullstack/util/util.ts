export function base10ToBase21(nb: number): string {
  if (!Number.isInteger(nb) || nb < 1) {
    return "";
  }

  let ret = new Array();
  const elements = "0123456789abcdefghijk";
  while (nb != 0) {
    ret.unshift(elements.charAt(nb % 21));
    nb = Math.floor(nb / 21);
  }
  return ret.join("");
}

export function isValidURL(text: string): boolean {
  const validator = new RegExp(
    /^https?:\/\/(?:www\.)?[-a-zA-Z0-9@:%._\+~#=]{1,256}\.[a-zA-Z0-9()]{1,6}\b(?:[-a-zA-Z0-9()@:%_\+.~#?&\/=]*)$/,
  );
  return validator.test(text);
}

export function lengthValidator(URL: string, maxLength: number): boolean {
  let ret = true;
  if (URL.length < 1) {
    ret = false;
  } else if (URL.length > maxLength) {
    ret = false;
  }
  return ret;
}
