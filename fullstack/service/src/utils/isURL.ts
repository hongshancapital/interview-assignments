export default (str: string): boolean => /^(https?:\/\/|data:)/.test(str);
