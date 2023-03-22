const fetch = require('node-fetch')

fetch('http://127.0.0.1:3080/api/domain/l2s', {
  method: 'post',
  headers: {
    'Content-Type': 'application/json'
  },
  body: JSON.stringify({
    lstr: 'xxyy1234500000000000000000000'
  })
}).then(res => res.json()).then(data => {
  console.log(data)
})

// 本地运行结果：

/*
$ node query.js
{
  code: 0,
  r: { sstr: '1hg1b1g83vxu2' },
  errMsg: '',
  timestamp: '2023-02-23T17:55:36.421Z'
}



*/
