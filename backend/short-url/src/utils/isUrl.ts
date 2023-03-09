const urlPattern = /(?:https?):\/\/(\w+:?\w*)?(\S+)(:\d+)?(\/|\/([\w#!:.?+=&%!\-/]))?/;

export function isUrl(url = '') {
    if (url === '') {
        return false;
    }

    return urlPattern.test(url);
}
