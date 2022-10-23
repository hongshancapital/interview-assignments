export const converts = {
  boolean(str: string): boolean | undefined {
    if (str === 'true') {
      return true;
    }
    if (str === 'false') {
      return false;
    }
    return undefined;
  },
  number(str: string): number | undefined {
    const num = Number(str);

    return Number.isFinite(num) ? num : undefined;
  },
  string(str: string): string {
    return str;
  },
};
