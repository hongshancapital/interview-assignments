import 'mocha';
import chai, { expect } from 'chai';
import chaiHttp from 'chai-http';
import app from '../app';

chai.use(chaiHttp);

describe('App server is running', () => {
  // it('/ path should respond status 404 without error', (done) => {
  //   chai.request(app)
  //     .get('/')
  //     .end((err, res) => {
  //       res.should.have.status(404);
  //       expect(err).to.be.equal(null);
  //       done();
  //     });
  // });

  // it('/api path should respond status 200 without error', (done) => {
  //   chai.request(app)
  //     .get('/api')
  //     .end((err, res) => {
  //       res.should.have.status(200);
  //       expect(err).to.be.equal(null);
  //       done();
  //     });
  // });

  // it('url(bpi) not api path should respond status 404 without error', (done) => {
  //   chai.request(app)
  //     .get('/bpi')
  //     .end((err, res) => {
  //       res.should.have.status(404);
  //       expect(err).to.be.equal(null);
  //       done();
  //     });
  // });
})
