
/**
 * @param username 
 * @returns 
 */
export function getUserInfo(username: string): any {
    if (username == 'fooTest') {
        return {
            'code': 1000,
            'message': 'success',
            'username': username
        }
    }

    //注册数据此处仅返回结果
    return {
        'code': 1001,
        'message': 'fail',
    }
};

/**
 * 验证用户信息
 * @param username 
 * @param password 
 * @param repeat_password 
 * @returns 
 */
export function checkUser(username: string, password: string, repeat_password: string): any {


    if (!username?.trim() || !password?.trim()) {
        return {
            'code': 1001,
            'message': 'user name or password is empty',
        }
    }
    if (password?.trim() != repeat_password?.trim()) {
        return {
            'code': 1001,
            'message': 'The two passwords are inconsistent',
        }
    }
    //验证用户名
    let checkUserName = /^[A-Za-z_][\w]+/i.test(username);
    if (false == checkUserName) {
        return {
            'code': 1001,
            'message': 'user name : Starts with an English letter or underscore and can only contain letters, underscores, or numbers',
        }
    }
    //验证密码长度
    if (password.length <= 6) {
        return {
            'code': 1001,
            'message': 'Password length should be more than 6 digits',
        }
    }
    //验证密码规则
    let checkPassword = /\d{3}/.test(password);
    if (false == checkPassword) {
        return {
            'code': 1001,
            'message': 'The password cannot contain more than 3 consecutive digits',
        }
    }
    return {
        'code': 1000,
        'message': 'success',
    }
}