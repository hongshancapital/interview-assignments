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

// integration tests
describe('### Basic server tests', function () {
  var server = require('../server');

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
      //.expect(200, done).expect('Content-Type', /json/)
      .end( (err, res) => {
        if (err) done(err);
        // GET access to the short URL
        console.log('  ### Shortened URL is: ' + '/' + res.body.hash);
        request(server)
          .get('/' + res.body.hash)
          .expect("Content-Type", /text/)
          .expect(302, done);
      });
  });
});