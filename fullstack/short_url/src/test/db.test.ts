import mysql from 'mysql';
import {getDbConnection, query} from '../db';

const mockControl = {
    createPoolSuccess: true,
    queryRes: {
        error: '',
        res: [],
    }
};

jest.mock('mysql', () => {
    const mockedConnection = {
        release: jest.fn(),
        query: jest.fn((sql:string, values:any, callback: (error: any | null, res: any) => void) => {
            if(mockControl.queryRes.error) {
                callback(mockControl.queryRes.error, []);
            } else {
                callback(null, mockControl.queryRes.res);
            }
        }),
    };
    const mockedPool = {
        getConnection: jest.fn((callback: (error: any|null, connection: any) => void) => {
            if(mockControl.createPoolSuccess){
                callback(null, mockedConnection);
            } else {
                callback('error', null);
            }
        })
    };
    return {
        createPool: jest.fn().mockReturnValue(mockedPool)
    };
});

describe('test DB wrapper', () => {

    test('test getDbConnection success', async () => {
        mockControl.createPoolSuccess = true;
        try{
            const connection = await getDbConnection();
            expect(connection).toBeTruthy();
        } catch (e) {
            fail(e);
        }
    });

    test('test getDbConnection fail', async () => {
        mockControl.createPoolSuccess = false;
        await expect(getDbConnection()).rejects.toBeTruthy();
    });
    
    test('test query success', async () => {
        mockControl.createPoolSuccess = true;
        mockControl.queryRes.error = '';
        mockControl.queryRes.res = [];
        const connection = await getDbConnection();
        expect(!connection).toBe(false);
        try{
            const res = await query(connection, 'select * from test', {});
            expect(res).toBeTruthy();
        }catch(e) {
            fail(e);
        }
    });

    test('test query fail', async () => {
        mockControl.createPoolSuccess = true;
        mockControl.queryRes.error = 'xxx error';
        const connection = await getDbConnection();
        expect(!connection).toBe(false);
        await expect(query(connection, 'select * from test', {})).rejects.toBeTruthy();
    });
    
});
