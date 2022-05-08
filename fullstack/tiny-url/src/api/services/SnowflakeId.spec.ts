import SnowflakeIdinstance from '../services/SnowflakeId';
describe('Test SnowflakeId class', () => {
    test('Test function getId,should return alphanumeric string of no more than 8 digits', () => {
        const sid = SnowflakeIdinstance.getId(1, true);
        expect(sid).toMatch(/^([a-zA-Z0-9]){1,8}$/);
    });
    test('Test function getId, return "-1" parameter is greater than 68719476735', () => {
        const sid = SnowflakeIdinstance.getId(68719476736);
        expect(sid).toEqual('-1');
    });
});
