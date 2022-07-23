/** @description 修正传入的easing字符串, 针对cubic-bezier传入不合法的情况 */
export function fixEasing(easing: string): string {
  if (['linear', 'ease', 'ease-in', 'ease-out', 'ease-in-out'].includes(easing)) {
    return easing;
  }
  if (easing.match(/^cubic-bezier\(.*\)$/)) {
    const str = easing.slice(13, -1);
    const numstrs = str.split(',');
    if (numstrs.length === 4) {
      try {
        if (numstrs.every((numstr: string) => {
          const num = parseFloat(numstr);
          return num >= 0 && num <= 1;
        })) {
          return easing;
        }
      } catch(e) {
        //
      }
    }
  }
  return 'linear';
}