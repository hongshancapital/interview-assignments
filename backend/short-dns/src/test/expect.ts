import * as chai from 'chai';
import sinonChai from 'sinon-chai';
import chaiJestSnapshot from 'chai-jest-snapshot';
import chaiSubset from 'chai-subset';

chai.use(sinonChai);
chai.use(chaiJestSnapshot);
chai.use(chaiSubset);

export const expect = chai.expect;
