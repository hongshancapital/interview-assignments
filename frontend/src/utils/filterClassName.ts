export function filterClassName(...classNames: any[]) {
  return classNames.filter((i) => typeof i === 'string').join(' ')
}