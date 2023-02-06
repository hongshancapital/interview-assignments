import { classnames } from '..';

describe('classnames', () => {
  it('should combine the classes of all string type into a class string', () => {
    const result = classnames('a', 'b', 'c');

    expect(result).toBe('a b c');
  });

  it('should combine the keys of object only the key with the value of true in object', () => {
    const classesObj = {
      a: true,
      b: false,
      c: true
    };

    const result = classnames(classesObj);

    expect(result).toBe('a c');
  });

  it('should correctly combine the class string when mixing object and string', () => {
    const classesObj = {
      a: true,
      b: false,
      c: true
    };

    const result = classnames('1', classesObj, '2', '3');

    expect(result).toBe('1 a c 2 3');
  });
});
