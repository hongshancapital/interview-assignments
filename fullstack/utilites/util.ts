import url from 'url';

import { SHORT_LENGTH } from './constant';


export function validateUrl(value: string) {
    return /[(http(s)?):\/\/(www\.)?a-zA-Z0-9@:%._\+~#=]{2,256}\.[a-z]{2,6}\b([-a-zA-Z0-9@:%_\+.~#?&//=]*)/i.test(
      value
    );
  }

export function isLongDomain(value: string) {
    const { path , hash } = url.parse(value);
    return (path ? path.substring(1) : '').length + (hash ? hash : '').length > SHORT_LENGTH;
}

export function isShortDomain(value: string) {
    const { path , hash } = url.parse(value);
    return (path ? path.substring(1) : '').length + (hash ? hash : '').length <= SHORT_LENGTH;
}

