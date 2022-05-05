import os from 'os';
export function getHostName(): string {
    return os.hostname();
}
export function getIPAddress(): string {
    let interfaces = require('os').networkInterfaces();
    for (var devName in interfaces) {
        var iface = interfaces[devName];
        for (var i = 0; i < iface.length; i++) {
            let alias = iface[i];
            if (alias.family === 'IPv4' && alias.address !== '127.0.0.1' && !alias.internal) {
                return alias.address;
            }
        }
    }
    return '';
}
export function toCodePoints(str: string): number[] {
    return str.split('').map(m => m.charCodeAt(0));
}

export function mathSum(arr: number[]): number {
    return arr.reduce((prev, current) => prev + current, 0);
}

export function randomRange(min: number, max: number): number {
    const range: number = max - min;
    return min + Math.round(Math.random() * range);
}
