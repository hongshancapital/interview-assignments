import "reflect-metadata";

import mongoService from '../../../src/services/mongo.service';
import { UserModel } from '../../../src/models/user.model';
import { User } from '../../../src/interfaces/user.interface';

describe('User model', () => {
  beforeAll(async () => {
    await mongoService.connect();
    await UserModel.deleteMany({});
  });

  afterAll(async () => {
    await mongoService.disconnect();
  });

  afterEach(async () => {
    await UserModel.deleteMany({});
  });

  it('should create and save a new user successfully', async () => {
    const newUser: User = new UserModel({
      uid: '1',
      username: 'testuser',
      password: 'password123',
      salt: 'somesalt',
    });

    const savedUser: User = await newUser.save();

    expect(savedUser._id).toBeDefined();
    expect(savedUser.uid).toBe(newUser.uid);
    expect(savedUser.username).toBe(newUser.username);
    expect(savedUser.password).toBe(newUser.password);
    expect(savedUser.salt).toBe(newUser.salt);
    expect(savedUser.createTime).toBeDefined();
    expect(savedUser.updateTime).toBeDefined();
    expect(savedUser.available).toBeTruthy();
  });

  it('should not save a user with a duplicate username', async () => {
    const firstUser: User = new UserModel({
      uid: '1',
      username: 'testuser',
      password: 'password123',
      salt: 'somesalt',
    });

    await firstUser.save();

    const secondUser: User = new UserModel({
      uid: '2',
      username: 'testuser',
      password: 'password456',
      salt: 'anothersalt',
    });

    await expect(secondUser.save()).rejects.toThrow();
  });
});
