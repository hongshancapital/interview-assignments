

type ClassArg = string | string[] | Record<string, boolean> | undefined

/**
 * 处理动态样式
 * @param arg ClassArg
 * @returns string
 * 
 * mergeClassName('a', ['b', 'c'], {
 *  'd': true,
 *  'e': false
 * }) 
 *  'a b c d'
 */
export const mergeClassName = (...arg: ClassArg[]): string => {
  const classArr: string[] = [];
  arg.forEach((item) => {
    if (typeof item === 'string') {
      classArr.push(item)
    } else if(typeof item === 'object') {
      if (Array.isArray(item)) {
        classArr.push(...item)
      } else {
        Object.keys(item).forEach(key => {
          if(item[key]) {
            classArr.push(key)
          }
        })
      }
    }
  })
  return classArr.join(' ')
}


export const setStyle = (target: HTMLElement, styles: Record<string, any>) => {
  const { style } = target;
  Object.keys(styles).forEach((attribute: any) => {
    style[attribute] = styles[attribute];
  });
}