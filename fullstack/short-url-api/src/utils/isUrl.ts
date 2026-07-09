const urlPattern = /(?:https?):\/\/(\w+:?\w*)?(\S+)(:\d+)?(\/|\/([\w#!:.?+=&%!\-\/]))?/;

export default function isUrl(url = '') {
  if (url === '') {
    return false;
  }

  return urlPattern.test(url);
}
