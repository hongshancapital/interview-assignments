

export function classnames(
    ...classNames: Array<
      string | { [key: string]: boolean } | string[] | undefined
    >
  ): string {
    const classes: string[] = [];
  
    for (const cls of classNames) {
      if (!cls) {
        continue;
      }
  
      if (typeof cls === 'string') {
        classes.push(cls);
      } else if (Array.isArray(cls)) {
        cls.forEach((item) => {
          if (item) {
            classes.push(item);
          }
        });
      } else if (typeof cls === 'object' && cls) {
        Object.keys(cls).forEach((key) => {
          if (cls[key]) {
            classes.push(key);
          }
        });
      }
    }
  
    return classes.join(' ');
  }



export function getPrefixCls(
  mainClassName: string,
  suffixClass?: { [key: string]: string | number | boolean | undefined },
  ignoreRootCls?: boolean,
): string {
  const suffixClassNames: string[] = [];

  if (suffixClass) {
    Object.keys(suffixClass).forEach((key) => {
      const value = suffixClass[key];

      if (value === false || value == null || value === '') {
        return;
      }

      const suffix =
        typeof value === 'string' || typeof value === 'number'
          ? `-${value}`
          : '';

      suffixClassNames.push(mainClassName + '-' + key + suffix);
    });
  }

  return classnames(
    ignoreRootCls ? undefined : mainClassName,
    suffixClassNames,
  );
}


  export function getKeys<T>(obj: T): Array<keyof T> {
    return Object.keys(obj) as Array<keyof T>;
  }

  

  export function mixin(
    Base: { prototype: unknown },
    ...args: Array<{ prototype: unknown }>
  ): void {
    const obj = Base.prototype as Record<string, unknown>;
  
    args.forEach((target) => {
      const prototype = target.prototype as Record<string, unknown>;
      const descriptor = Object.getOwnPropertyDescriptors(prototype);
      Object.keys(descriptor).forEach((key) => {
        Object.defineProperty(obj, key, descriptor[key]);
      });
    });
  }
  