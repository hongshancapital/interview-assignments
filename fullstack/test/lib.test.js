const { generate } = require('../src/lib/shortUrl');
// const test = require("node:test");

test('generat function not empty', () => {
    expect(generate('https://wo.wo')).not.toBe('')
})

test('generat function return text length < 8', () => {
    expect(generate('https://wo.wo').length).toBeLessThan(8)
})

