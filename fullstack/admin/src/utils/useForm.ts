export * from 'react-use-form-state';

export const PHONE_REGEX = /^1[3-9]\d{9}$/
export const CODE_REGEX = /[0-9]{6}/
export const PASSWORD_REGEX = /[0-9A-Za-z_]{6,}/

export function isPristine(formState: {
  pristine: any
  errors: any
}, filters: string[] = []): boolean {
  const { pristine, errors } = formState
  return !!Object.keys(pristine).filter(v => !filters.includes(v)).find(
    key => !!pristine[key],
  ) || !!Object.keys(errors).filter(v => !filters.includes(v)).length;
}