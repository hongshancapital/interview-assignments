import { expect } from 'chai';
import { generate } from '../src/component/randomcode';

describe('random code test', () => {
  it('should generate a series of url tolerated characters with specific length', () => {
    expect(generate()).to.match(/^[0-9a-zA-Z_-]{8}$/);
  });
});
