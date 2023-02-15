export function isEmpty(val?: string | null | number) {
  return typeof val === 'undefined' || val === null || val === '';
}

export function isLink(val?: string) {
  return !!(val && val.length <= 8182 && /^https?:\/\/.+$/.test(val));
}

export function isID(val?: string) {
  return !!(val && /^.{8}$/.test(val));
}
