import axios from "axios"

describe('url transform tests', () => {
  it('长链接转短链接', async () => {
    const response = await axios.post(
      'http://localhost:3000/shortUrl',
      {
        longUrl: 'https://www.baidu.com'
      }
    )
    expect(response.status).toEqual(200)
  })

  it('短链接获取', async () => {
    const response = await axios.get('http://localhost:3000/mzOO7nsM',)
    expect(response.status).toEqual(200)
  })
})