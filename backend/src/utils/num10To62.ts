export default (num: number): string => {
    if (num === 0) {
        return '0';
    }
    const digits = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
    let result = '';

    while (num > 0) {
        result = digits[num % digits.length] + result;
        num = parseInt(`${num / digits.length}`, 10);
    }
    return result;
};
