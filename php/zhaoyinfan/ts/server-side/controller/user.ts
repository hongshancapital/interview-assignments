
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
    return {
        'code': 1001,
        'message': 'fail',
    }
};