import { useRef } from 'react';

export const classNames = (...args: Array<string | {[key: string]: boolean} | undefined>) => {
  return args.reduce<string[]>((p, c) => p.concat(
    typeof c === 'string' ? c : Object.keys(c || {}).map(v => (c || {})[v] ? v : ''),
  ), []).filter(Boolean).join(' ');
}

export const useUid = () => useRef(Math.random().toString(36).substring(2, 7)).current;