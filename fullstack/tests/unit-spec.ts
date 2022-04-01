import { decodeBase62Str2Id, encodeId2Base62Str } from '../src/libs/utils';
describe('Utils Function Test', () => {
  it('#1 decodeBase62Str2Id should work', () => {
    const id = decodeBase62Str2Id('bcd');
    expect(id).toBe(3971);
    const id2 = decodeBase62Str2Id('bcc');
    expect(id2).toBe(3970);
  });

  it('#2 encodeId2Base62Str should work', () => {
    expect(encodeId2Base62Str(3971)).toBe('bcd');
    expect(encodeId2Base62Str(3970)).toBe('bcc');
  });
});
