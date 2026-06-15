const axios = require('axios');
const apiController = require('../controller/api.controller');

test('根据长网址获取短网址', async () => {
   const response = await axios.get('http://localhost:1234/api/findShortUrl/asdadsadadasdasdasdadasdadasd');
   expect(response.data.code).toEqual(200)
})

test('根据短网址获取长网址', async () => {
   const response = await axios.get('http://localhost:1234/api/findLongUrl/94ohQK');
   expect(response.data.code).toEqual(200)
})
