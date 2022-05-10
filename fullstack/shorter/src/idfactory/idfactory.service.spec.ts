import { IdfactoryService } from './idfactory.service';

describe('Test id generator', () => {
  it('module getNextId', () => {
    const idfactoryService = new IdfactoryService();
    const first = idfactoryService.getNextId();
    expect(first).toMatch(/^[a-zA-Z0-9]{1,8}$/g);
    const second = idfactoryService.getNextId();
    expect(second).toMatch(/^[a-zA-Z0-9]{1,8}$/g);
    expect(first === second).toBeFalsy();
  });
});