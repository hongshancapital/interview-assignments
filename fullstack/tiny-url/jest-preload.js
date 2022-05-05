jest.setTimeout(30000);
/**/
global.console = {
    log: jest.fn(), // console.log are ignored in tests

    // Keep native behaviour for other methods, use those to print out things in your own tests, not `console.log`
    error: console.error,
    warn: console.warn,
    info: console.info,
    debug: console.debug,
};
/**/
require('mysql2/node_modules/iconv-lite').encodingExists('foo');
