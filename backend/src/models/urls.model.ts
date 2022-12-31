import { ShortUrl } from '@/interfaces/urls.interface';

// 说明: 为方便运行，这里使用一个数组代替真实DB

const ShortUrlModel: ShortUrl[] = [
    { id: 1, short: '12345678', long: 'http://abc.com/abc.html' },
];

export default ShortUrlModel;
