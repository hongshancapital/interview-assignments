export const formatList = (item: string | string[]): string[] => {
  if (Array.isArray(item)) {
    return item;
  }
  return [item];
};
