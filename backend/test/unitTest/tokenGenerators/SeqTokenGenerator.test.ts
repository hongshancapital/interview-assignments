import SeqTokenGenerator from "../../../src/tokenGenerators/SeqTokenGenerator";
import { mockRedis } from "../../mock/mockRedis";
import sinon, { SinonSandbox, stub } from "sinon";
import RedisFactory from "../../../src/cache/RedisFactory";
import assert from "assert";
import _ from "lodash";

describe("seq token generator tests", function() {
  let sandbox: SinonSandbox;

  this.beforeAll(() => {
    sandbox = sinon.createSandbox();
    SeqTokenGenerator.reset();
  })

  beforeEach(() => {
    mockRedis(sandbox);
  })

  it("init seq token generator, latest seq fetched from redis is 104", async function() {
    stub(RedisFactory.getClient(), 'get').callsFake(async key => {
      return "11G";
    })

    const generator = await SeqTokenGenerator.getInstance();
    assert.equal(generator.latestSeq, 104);
  })

  it("fetch token from generator", async function() {
    stub(RedisFactory.getClient(), 'get').callsFake(async key => {
      return "11G";
    })

    stub(_, 'random').callsFake(() => {
      return 1;
    })

    const generator = await SeqTokenGenerator.getInstance();
    let token = generator.generateToken();
    assert.equal(token, "11H");

    token = generator.generateToken();
    assert.equal(token, "11I");

    token = generator.generateToken();
    assert.equal(token, "11J");

    assert.equal(generator.store.length, generator.batchCount - 3);
  })

  afterEach(() => {
    sandbox.restore();
  })
})