type ClassNameType = string | Record<string, boolean>

export const classNames = (arg: ClassNameType, ...args: Array<ClassNameType>) => {
    const allArguments = [arg, ...(args || [])]
    const __isString = (a: ClassNameType) =>
        Object.prototype.toString.call(a).includes('String')
    const __genStr = (r: Record<string, boolean>) => {
        return Object.keys(r).reduce((ret, key) => ret += r[key] ? ` ${key}` : '', '').trim()
    }
    

    let result = allArguments.reduce((ret: string[], arg) => {
        ret = ret.concat(__isString(arg) ? arg as string : __genStr(arg as Record<string, boolean>))
        return ret
    }, [] as Array<string>).join(' ')


    return (result as string).trim()
}
