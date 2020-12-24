var request = require('supertest');
var mongoose = require('mongoose');

process.env.NODE_ENV = 'test';

// clean up the database before running
before(function (done) {

  function clearDB() {
    for (var i in mongoose.connection.collections) {
      mongoose.connection.collections[i].deleteOne(function() {});
    }
    return done();
  }

  if (mongoose.connection.readyState === 0) {
    mongoose.connect('mongodb://localhost/urlShortener', function (err) {
      if (err) {
        throw err;
      }
      return clearDB();
    });
  } else {
    return clearDB();
  }
});

// route unit tests
describe('### Basic server tests', function () {
  var server = require('../server');
  var shortURL;

  it('### Basic GET responds to /', function testSlash(done) {
  request(server)
    .get('/')
    .expect("Content-Type", /text/)
    .expect("Hello World")
    .expect(200, done);
  });

  it('### Checking 404 page', function testPath(done) {
    request(server)
      .get('/foo/bar')
      .expect(404, done);
  });


  it('### Shorten URL and GET access', function testShortenURL(done) {
    request(server)
      .post('/api/shorten')
      .type("form")
      .send({ url: "www.mathworks.com" })
      .end( (err, res) => {
        if (err) done(err);
        // GET access to the short URL
        shortURL = res.body.hash;
        console.log('  ### Shortened URL is: ' + '/' + shortURL);
        request(server)
          .get('/' + shortURL)
          .expect("Content-Type", /text/)
          .expect(302, done);
      });
  });

  it('### Testing redirect route API', function testRedirectURL(done) {
    console.log('  ### redirect route hash: ' + '/' + shortURL);
    request(server)
      .get('/api/redirect?hash' + shortURL)
      .set({'hash': shortURL})
      .expect(200, done).expect('Content-Type', /json/)
      .expect({'url':'www.mathworks.com'});
  });

  it('### Invalid shortened URL', function testInvalidShortURL(done) {
    shortURL = '12345';
    console.log('  ### redirect route hash: ' + '/' + shortURL);
    request(server)
      .get('/api/redirect?hash' + shortURL)
      .set({'hash': shortURL})
      .expect(400, done).expect('Content-Type', /json/)
      .expect({'error':'This link may have expried'});
  });
});