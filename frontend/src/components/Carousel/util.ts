/**
 * get the next slice index
 * @param {number} sliceCount  the slice count
 * @param {number} [currentIndex]  the current slice index
 * @returns {number}  the next slice index
 */
export function getNextIndex(sliceCount: number, currentIndex?: number): number {
  return currentIndex == null ? 0 : (currentIndex + 1) % sliceCount;
}

/**
 * get the previous slice index
 * @param {number} sliceCount  the slice count
 * @param {number} [currentIndex]  the current slice index
 * @returns {number}  the previous slice index
 */
export function getPreviousIndex(sliceCount: number, currentIndex?: number): number {
  return currentIndex == null ? 0 : currentIndex ? currentIndex - 1 : sliceCount - 1;
}

/**
 * whether the slice is adjacent with the current slice
 * @param {number} sliceIndex  the slice index to check
 * @param {number} sliceCount  the slice count
 * @param {number} [currentIndex]  the current slice index
 * @returns {boolean}  true if the slice is equals or next or previous to the current slice
 */
export function isAdjacentSlice(sliceIndex: number, sliceCount: number, currentIndex?: number) {
  const current = currentIndex == null ? 0 : currentIndex;
  return sliceIndex === current || sliceIndex === getNextIndex(sliceCount, current) || sliceIndex === getPreviousIndex(sliceCount, current);
}
