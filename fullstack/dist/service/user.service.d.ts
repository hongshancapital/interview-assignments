import { IUserOptions } from '../interface';
export declare class UserService {
    getUser(options: IUserOptions): Promise<{
        uid: number;
        username: string;
        phone: string;
        email: string;
    }>;
}
