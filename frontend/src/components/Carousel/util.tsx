
export function isNumber(value: any): value is number {
    return typeof value === 'number';
}

export function isNull(value: any): value is null {
    return value === null;
}

export function isUndefined(value: any): value is undefined {
    return value === undefined;
}