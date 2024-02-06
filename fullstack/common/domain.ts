/**
 * test环境域名和prod环境域名
 */
enum Domains {
    test = 'https://test.com/',
    prod = 'https://prod.com/',
}

// 读取环境变量，默认prod
const env: string = process.env.NODE_ENV || 'prod';
// console.log('env', env);

export default Domains[env as keyof typeof Domains];
