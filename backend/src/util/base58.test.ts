import { encodeBase58 } from "./base58";

describe('encodeBase58', () => {
    it('should encode a number to a base58 string', () => {
      // 编写测试用例，测试正常情况下的编码
      const num1 = BigInt(12345);
      expect(encodeBase58(num1)).toBe('000004fr');
      
      const num2 = BigInt(0);
      expect(encodeBase58(num2)).toBe('00000000');
    });
  
    it('should pad with zeros when length is less than minLength', () => {
      // 编写测试用例，测试长度不够时的补零
      const num = BigInt(42);
      expect(encodeBase58(num)).toBe('0000000j');
    });
  });