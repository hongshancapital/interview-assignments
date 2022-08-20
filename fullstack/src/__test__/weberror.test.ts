const http = require('http');

describe('web error', () => {
    it('should handle error', () => {
        const mError = new Error('test error');
        const mServer = {
            listen: jest.fn().mockReturnThis(),
            on: jest.fn().mockImplementationOnce((event, handler) => {
                // handler is the original callback, the mError variable will be passed into the original callback.
                handler(mError);
            }),
        };
        const createServerSpy = jest.spyOn(http, 'createServer').mockImplementationOnce(() => mServer);
        const logSpy = jest.spyOn(console, 'log');
        require('../src/web');
        expect(createServerSpy).toBeCalledTimes(1);
        expect(mServer.on).toBeCalledWith('error', expect.any(Function));
        expect(logSpy).toBeCalledWith(mError.message);

    });
});