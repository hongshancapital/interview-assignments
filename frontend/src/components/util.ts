const CssPrefix: string = 'cj';

/**
 * 转换css类名，自动添加css类名前辍
 * @param cssClassNames
 */
export function prefix(cssClassNames: string | string[]): string {
  const _classnames = typeof cssClassNames === 'string' ? cssClassNames.replace(/\s+/g, ' ').split(' ') : cssClassNames;

  if (Array.isArray(_classnames)) {
    return _classnames.map((it) => `${CssPrefix}-${it}`).join(' ');
  }
  throw new Error('prefix 参数无效');
}
/**
 * 打印调试信息
 */
export const debug =
  process.env.NODE_ENV === 'development'
    ? (...args: unknown[]) => {
        console.log.apply(console, args);
      }
    : (...args: unknown[]) => {};
