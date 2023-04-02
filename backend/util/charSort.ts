const charactersBase = 'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789';

function sort(str: string) {
    let arr = Array.from({ length: charactersBase.length }, (_, index) => charactersBase.charAt(index)).sort((a: string, b: string) => Math.random() > 0.5 ? 1 : -1);
    return arr.toString().replace(/,/g, '');
}
console.log(sort(charactersBase));