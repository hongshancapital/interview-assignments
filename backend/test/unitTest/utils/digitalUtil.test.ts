import assert from "assert"
import { digital10to62, digital62to10 } from "../../../src/utils/digitalUtil"

describe("digital util tests", function() {
  it("[10 to 62] 11 => b", function() {
    assert.equal(digital10to62(11), "b");
  })

  it("[10 to 62] 107 => 1J", function() {
    assert.equal(digital10to62(107), "1J");
  })

  it("[10 to 62] 3521614606207 => ZZZZZZZ", function() {
    assert.equal(digital10to62(3521614606207), "ZZZZZZZ");
  })

  it("[62 to 10] a => 10", function() {
    assert.equal(digital62to10("a"), 10);
  })

  it("[62 to 10] ZZZZZZZ => 3521614606207", function() {
    assert.equal(digital62to10("ZZZZZZZ"), 3521614606207);
  })
})