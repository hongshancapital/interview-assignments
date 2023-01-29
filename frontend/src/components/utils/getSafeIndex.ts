export const getSafeIndex = (index: number, count: number) => {
  return index < 0 ? count - 1 : index % count;
};
