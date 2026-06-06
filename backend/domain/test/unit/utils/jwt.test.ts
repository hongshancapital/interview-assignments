import { generateToken, verifyToken } from '../../../src/utils/jwt';

describe('utils: jwt', () => {
  const payload = { userId: '12345' };
  const secretKey = 'mysecretkey';

  it('should generate a token with the correct payload and secret key', async () => {
    const expiresIn = 3600;
    const token = await generateToken(payload, expiresIn, secretKey);
    expect(token).toBeTruthy();

    const decoded = await verifyToken(token, secretKey) as any;
    expect(decoded.userId).toBe(payload.userId);
  });

  it('should verify a token with the correct secret key', async () => {
    const expiresIn = 3600;
    const token = await generateToken(payload, expiresIn, secretKey);
    expect(token).toBeTruthy();

    const decoded = await verifyToken(token, secretKey) as any;
    expect(decoded.userId).toBe(payload.userId);
  });

  it('should throw an error when verifying a token with the wrong secret key', async () => {
    const expiresIn = 3600;
    const token = await generateToken(payload, expiresIn, secretKey);
    expect(token).toBeTruthy();

    const wrongSecretKey = 'wrongsecretkey';
    await expect(verifyToken(token, wrongSecretKey)).rejects.toThrow();
  });
  
});
