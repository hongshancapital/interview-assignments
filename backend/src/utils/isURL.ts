export default (str: unknown): boolean => typeof str === 'string' && /^(https?:\/\/)/.test(str);
