/**
 * classnames('x-1 aa bb', 'cc', 'dd', { a:true, dd: false })
 * @param classes string | object
 * @returns string
 */
export function classnames(...classes: string[] | {
  [key: string]: boolean
}[]) {
  const cla = classes.map(v => {
    if (typeof v === 'object') {
      return Object.keys(v).filter(k => v[k]).join(' ')
    } else {
      return v
    }
  }).filter(Boolean);
  return Array.from(new Set(cla)).join(' ')
}