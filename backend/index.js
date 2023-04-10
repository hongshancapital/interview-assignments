const murmurhash = require('murmurhash')
// const uuid62 = require('uuid62');

// import { encode } from 'lex62ts'
const lex62ts = require('lex62ts')


const murmur = murmurhash.v3("https://time.geekbang.org/column/article/80850", "some seed value (optional)")

console.log('murmur: ', murmur);

// const uuid = uuid62.encode(murmur);

// console.log('uuid: ', uuid)

console.log('encode: ',lex62ts.encode(murmur))