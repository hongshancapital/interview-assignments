import * as _ from 'ts-toolbelt';
function pick<K extends string | number | symbol>(keys:readonly K[]) {
    return function <T extends readonly [any, ...any] | object>(object: T){
        const allKeys = Object.keys(object) as K[]
        return allKeys.reduce((newObejct, key) => {
            if (!keys.find(k => k === key)) {
                return newObejct
            }
            return {
                ...newObejct,
                [key]: (object as Record<K,unknown>)[key]
            }
        }, {} as T extends readonly [any, ...any]
        ? {
            [P in K as P extends string | number
            ? _.N.Greater<`${T['length']}`, `${P}`> extends 1
            ? P
            : never
            : never]: P extends keyof T ? T[P] : T[number];
        }
        : { [P in keyof T as P extends K ? P : never]: T[P] })

    }
}

export default pick