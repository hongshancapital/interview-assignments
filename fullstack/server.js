// require
const express = require('express');
const mongoose = require('mongoose');
const bodyParser = require('body-parser');
// load model
const URL = require('./models/URLs');

// init 
const app = express();

// middleware
app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());

// connect to db
mongoose.connect('mongodb://localhost/urlShortener', {
    useNewUrlParser: true, useUnifiedTopology: true
}).then(() => console.log('  ### MongoDB connected'))
  .catch( err => console.log('err'));

// route middleware
const shorten = require('./routes/api/shorten');
app.use('/api/shorten', shorten);
const redirect = require('./routes/api/redirect');
app.use('/api/redirect', redirect);

// get short URL e.g. http://localhost:5000/5rxpPzRB
app.get('/:shortURL', (req, res)=>{
  const shortURL = req.params.shortURL;
  // pass in the short url
  URL.findOne({_id: shortURL}, (err, doc) => {
    if(doc){
      // redirect to the real url
      console.log('  ### Redirect to: ' + doc.url);
      res.redirect('http://' + doc.url);
    }else{
      // redirect to home
      res.redirect('/');
    }
  })
});

// testing
app.get('/', (req, res)=>{
  res.send('Hello World');
});

// port
app.listen(process.env.PORT || 5000);  // 5000 for debug & test

module.exports = app