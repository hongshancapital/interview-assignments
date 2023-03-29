const {
    findByUrl,
    findByShort,
    create
} = require('../src/service/index');
const { generate } = require('../src/lib/shortUrl');
test('findByUrl', async () => {
    const [ err, record ] = await findByUrl('https://example12.com')
    console.log('record in test', record)
    expect(err).toBe(null)
    expect(record).not.toBe(null)
    expect(record.urlCode).toBe('ZFvIJr')

    const [ newError, newRecord ] = await findByUrl('https://example1234.com')
    expect(newRecord).toBe(null)
    expect(newError).toBe(null)
})
//
test('findByShort', async () => {
    const [ err, record ] = await findByShort('ZFvIJr')
    expect(err).toBe(null)
    expect(record).not.toBe(null)
    expect(record.longUrl).toBe('https://example12.com')

    const [ newError, newRecord ] = await findByShort('ZFvIJh')
    expect(newRecord).toBe(null)
    expect(newError).toBe(null)
})

test('create', async () => {
    const longUrl = 'https://example7778.com'
    const urlCode = generate(longUrl)
    const [ err, record ] = await create(urlCode, longUrl)
    expect(err).not.toBe(null)
    expect(record).not.toBe(null)
})