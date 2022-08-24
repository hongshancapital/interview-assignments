export function classnames(...args: Array<string | Array<string> | Record<string, boolean> | undefined>) {
  const classNames: string[] = [];

  args.forEach((item) => {
    if (item === void 0) return;
    if (typeof item === "string") {
      classNames.push(item);
    }
    if (Array.isArray(item)) {
      classNames.push(...item.filter((className) => !classNames.includes(className)));
    }
    if (Object.prototype.toString.call(item) === "[object Object]") {
      for (let key in item as Record<string, boolean>) {
        if ((item as Record<string, boolean>)[key]) {
          if (!classNames.includes(key)) {
            classNames.push(key);
          }
        } else {
          if (classNames.includes(key)) {
            classNames.splice(classNames.indexOf(key), 1);
          }
        }
      }
    }
  });

  return classNames.join(" ");
}
