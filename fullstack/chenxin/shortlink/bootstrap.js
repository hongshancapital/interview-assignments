const { Framework } = require('@midwayjs/express');
const { Bootstrap } = require('@midwayjs/bootstrap');

const web = new Framework().configure({
  port: 7001,
});

Bootstrap.load(web)
  .run()
  .then(() => {
    console.log('Your application is running at http://localhost:7001');
  });
