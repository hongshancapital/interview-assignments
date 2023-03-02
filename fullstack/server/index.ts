import express from 'express';
const app = express()
const port = 3000

app.get('/', (req: any, res: any) => {
  res.send('Hello World!')
})

app.post('/creatShortLink', (req: any, res: any) => {

})

app.get('/getLongLink', (req: any, res: any) => {

})

app.listen(port, () => {
  console.log(`Example app listening on port ${port}`)
})