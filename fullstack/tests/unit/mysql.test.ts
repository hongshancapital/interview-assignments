import mysqlFuncs from "../../database/mysql";

describe("Unit tests of <functions about database>", () => {
  describe("Test function <dbConnect>", () => {
    test("Normal connection with correct info", async () => {
      const connectionStatus = await mysqlFuncs.dbConnect({
        host: "127.0.0.1",
        user: "root",
        password: "123456",
      });
      expect(connectionStatus).toEqual("connect ok");

      await mysqlFuncs.closeDbConnection();
    });

    test("Connect again before deconnect", (done) => {
      mysqlFuncs
        .dbConnect()
        .then(() => {
          return mysqlFuncs.dbConnect();
        })
        .then(async (res) => {
          expect(res).toEqual("already connect");
          await mysqlFuncs.closeDbConnection();
          done();
        });
    });

    test("Failed connection with wrong config", async () => {
      const connectionStatus = await mysqlFuncs
        .dbConnect({
          host: "127.0.0.1",
          user: "oot",
          password: "123456",
        })
        .catch((err) => {
          expect(err).toMatch("Failed to connect to database.");
        });
      expect(connectionStatus).toBeUndefined();
    });
  });

  describe("Test function <closeDbConnection>", () => {
    test("Close connect successfully", (done) => {
      mysqlFuncs
        .dbConnect()
        .then(() => mysqlFuncs.closeDbConnection())
        .then((res) => {
          expect(res).toMatch("connect closed");
          done();
        });
    });

    test("Close connect unsuccessfully", (done) => {
      mysqlFuncs.closeDbConnection()
        .then((res) => {
          expect(res).toMatch("Failed to close db connection.");
          done();
        });
    });
  });

  describe("Test function <dbInit>", () => {
    beforeAll((done) => {
      mysqlFuncs.dbConnect().then(() => done());
    });

    afterAll((done) => {
      mysqlFuncs.closeDbConnection().then(() => done());
    });

    test("Normal init", async () => {
      const initStatus = await mysqlFuncs.dbInit();
      expect(initStatus).toEqual("init ok");
    });
  });

  describe("Test function <insertTinyURL>", () => {
    beforeAll((done) => {
      mysqlFuncs
        .dbConnect()
        .then(() => mysqlFuncs.dbInit())
        .then(() => mysqlFuncs.emptyTinyURL())
        .then(() => done());
    });

    afterAll((done) => {
      mysqlFuncs.closeDbConnection().then(() => done());
    });

    test("Normal insert", (done) => {
      const sURL = "https://bitly.com/09a",
        oURL = "https://baidu.com";
      mysqlFuncs
        .insertTinyURL(sURL, oURL)
        .then((res) => {
          return mysqlFuncs.findShortenedURLByOrignal("https://baidu.com");
        })
        .then((res) => {
          expect(res).toEqual(sURL);
          done();
        });
    });

    test("Insert empty short URL", (done) => {
      const sURL = "",
        oURL = "https://baidu.com";
      mysqlFuncs.insertTinyURL(sURL, oURL).then((res) => {
        expect(res).toMatch("Failed to insert the shortened URL into db.");
        done();
      });
    });
  });

  describe("Test function <findMaxIdInTinyURL>", () => {
    beforeAll((done) => {
      mysqlFuncs
        .dbConnect()
        .then(() => mysqlFuncs.dbInit())
        .then(() => mysqlFuncs.emptyTinyURL())
        .then(() => done());
    });

    afterAll((done) => {
      mysqlFuncs.closeDbConnection().then(() => done());
    });

    test("Empty table", () => {
      mysqlFuncs.findMaxIdInTinyURL().then((res) => expect(res).toBe(0));
    });

    test("1 row in table", (done) => {
      // insert 1 row
      const sURL = "https://bitly.com/09a",
        oURL = "https://baidu.com";
      mysqlFuncs
        .insertTinyURL(sURL, oURL)
        .then((msg) => {
          return mysqlFuncs.findMaxIdInTinyURL();
        })
        .then((res) => {
          expect(res).toEqual(1);
          done();
        });
    });
  });

  describe("Test function <findOrignalURLByShort>", () => {
    beforeAll((done) => {
      mysqlFuncs
        .dbConnect()
        .then(() => mysqlFuncs.dbInit())
        .then(() => mysqlFuncs.emptyTinyURL())
        .then(() => done());
    });

    afterAll((done) => {
      mysqlFuncs.closeDbConnection().then(() => done());
    });

    test("Normal search", (done) => {
      // insert 1 row
      const sURL = "https://bitly.com/09a",
        oURL = "https://baidu.com";
      mysqlFuncs
        .insertTinyURL(sURL, oURL)
        .then(() => {
          return mysqlFuncs.findOrignalURLByShort(sURL);
        })
        .then((res) => {
          expect(res).toEqual(oURL);
          done();
        });
    });

    test("URL not exist", (done) => {
      const sURL = "https://bitly.com/2w09a";
      mysqlFuncs.findOrignalURLByShort(sURL).then((res) => {
        expect(res).toEqual("");
        done();
      });
    });

    test("Input empty URL", (done) => {
      const sURL = "";
      mysqlFuncs.findOrignalURLByShort(sURL).then((res) => {
        expect(res).toMatch("invalid short URL length");
        done();
      });
    });
  });

  describe("Test function <findShortenedURLByOrignal>", () => {
    beforeAll((done) => {
      mysqlFuncs
        .dbConnect()
        .then(() => mysqlFuncs.dbInit())
        .then(() => mysqlFuncs.emptyTinyURL())
        .then(() => done());
    });

    afterAll((done) => {
      mysqlFuncs.closeDbConnection().then(() => done());
    });

    test("Normal search", (done) => {
      // insert 1 row
      const sURL = "https://bitly.com/09a",
        oURL = "https://baidu.com";
      mysqlFuncs
        .insertTinyURL(sURL, oURL)
        .then(() => {
          return mysqlFuncs.findShortenedURLByOrignal(oURL);
        })
        .then((res) => {
          expect(res).toEqual(sURL);
          done();
        });
    });

    test("URL not exist", (done) => {
      const oURL = "https://www.baidu.com";
      mysqlFuncs.findShortenedURLByOrignal(oURL).then((res) => {
        expect(res).toEqual("");
        done();
      });
    });

    test("Input empty URL", (done) => {
      mysqlFuncs.findShortenedURLByOrignal("").then((res) => {
        expect(res).toMatch("invalid original URL length");
        done();
      });
    });
  });
});
