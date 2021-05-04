import { User } from '../types';

const users: User[] = [];

export const registerUser = (newUser: User) => {
  users.push(newUser);
};