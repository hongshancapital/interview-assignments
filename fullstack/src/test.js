const chai = require("chai");
const chaiAsPromised = require("chai-as-promised");
const chaiHttp = require('chai-http');

chai.use(chaiAsPromised);
chai.use(chaiHttp);