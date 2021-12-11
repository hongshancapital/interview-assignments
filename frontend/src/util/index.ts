
export type UnionOmit<T, K> = T & Omit<K, keyof T>;

export interface IElementSize {
    width: number;
    height: number;
}
  
export interface IElementPosition extends IElementSize {
    top: number;
    right: number;
    bottom: number;
    left: number;
}
  
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


export function getDefaultElementSize(): IElementSize {
    return {
      width: 0,
      height: 0,
    };
}

export function getElementSize(element: HTMLElement): IElementSize {
  const { width, height } = element.getBoundingClientRect();
  return {
    width,
    height
  };
}
