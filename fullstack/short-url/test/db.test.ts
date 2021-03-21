import LevelDb from "../src/db/db";
LevelDb.idTable = {}
LevelDb.urlTable = {}


test("setId should return true", (done) => {
  LevelDb.idTable.put = jest.fn().mockResolvedValue(true)
  LevelDb.setId(123).then(data=>{
    expect(data).toBe(true);
    done();
  })
});
test("getCurrentId should return 123", (done) => {
  LevelDb.idTable.get = jest.fn().mockResolvedValue(123)
  LevelDb.getCurrentId().then(data=>{
    expect(data).toBe(123);
    done();
  })
});

test("getOriginUrl should return correct value", (done) => {
  LevelDb.urlTable.get = jest.fn().mockResolvedValue("http://www.abc.com")
  LevelDb.getOriginUrl("a").then(data=>{
    expect(data).toBe("http://www.abc.com");
    done();
  })
});
test("setUrl should return true", (done) => {
  LevelDb.urlTable.put = jest.fn().mockResolvedValue(true)
  LevelDb.setUrl("a","url").then(data=>{
    expect(data).toBe(true);
    done();
  })
});
