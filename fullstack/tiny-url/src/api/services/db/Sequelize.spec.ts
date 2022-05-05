import Mysqlinstance from './Sequelize';
describe('Test Mysql Sequelize class', () => {
    afterAll(async () => {
        await Mysqlinstance.close();
    });
    test('Test function update step', async () => {
        expect(await Mysqlinstance.updateStep(10000n)).toHaveBeenCalled;
    });
    /**
    test('Test function get id', async () => {
        expect(await Mysqlinstance.dequeue()).toBeGreaterThanOrEqual(1n);
    });
    /**/
    test('Test function get id', () => {});
});
