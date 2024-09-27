const frisby = require('frisby');

it('should be a teapot', function () {
    return frisby.post('http://localhost:3000/shorturl', {
        body: {
            "url": "https://stackov5555erflow.com/qu23estions/62265349444454/nodejs-with-ts-module-declares-component-locally-but-it-is-not-exported"
        }
    }).expect('status', 200);
});

it('no exist short url', function () {
    return frisby.get('http://localhost:3000/t/ax5y78', ).expect('json', {
        errorMessage: 'not found'
    })
})

it('return exist short url', function () {
return frisby.get('http://localhost:3000/t/ZFvIJr', ).expect('json', 'longUrl', 'https://example12.com')
})