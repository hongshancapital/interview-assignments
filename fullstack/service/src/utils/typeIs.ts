type AllType = 'string' | 'number' | 'bigint' | 'nan' | 'boolean' | 'symbol' | 'undefined' | 'object' | 'date' | 'function' | 'array' | 'null'

export function typeIs<T>(input: T, target: AllType): boolean
export function typeIs<T>(input: T): AllType

export function typeIs<T>(input: T, target?: AllType) {
    if (Array.isArray(input)) {
        return !target ? 'array' : target === 'array';
    } else if (input === null) {
        return !target ? 'null' : target === 'null';
    } else if (typeof input === 'number' && isNaN(input)) {
        return !target ? 'nan' : target === 'nan';
    } else if (input instanceof Date) {
        return !target ? 'date' : target === 'date';
    }

    return !target ? typeof input : target === typeof input;
}
