import { bootstrap } from "../../server"
import { shortUrlMock, testData } from "../../mock/ShortUrl.mock"
import * as request from "supertest"
let server
let app
let dataSource
let client

describe("test ShortUrlController", () => {
	beforeAll(async () => {
		const myApp = await bootstrap()
		app = myApp.app
		server = myApp.server
		dataSource = myApp.dataSource
		client = myApp.client
	})
	afterAll(async () => {
		await server.close()
		await dataSource.destroy()
		await client.disconnect()
	})

	it("post /api/shortUrl", async () => {
		const longUrl = testData.exits.longUrl
		const res = await request(app).post("/api/shortUrl").send({ longUrl })
		expect(res.status).toBe(200)
		expect(res.body.shortUrl).toBe(testData.exits.shortUrl)
		expect(res.body.longUrl).toBe(longUrl)
		expect(res.body.message).toBe("success")
	})

	it("exist long url post /api/shortUrl", async () => {
		// await shortUrlMock.add(testData.exits.longUrl)

		const longUrl = testData.exits.longUrl
		const res = await request(app).post("/api/shortUrl").send({ longUrl })

		const res2 = await request(app).post("/api/shortUrl").send()
		expect(res2.body.error).toBe(400)

		const res3 = await request(app).post("/api/shortUrl").send({
			longUrl: "22",
		})
		expect(res2.body.error).toBe(400)

		expect(res.status).toBe(200)
		expect(res.body.shortUrl).toBe(testData.exits.shortUrl)
		expect(res.body.longUrl).toBe(longUrl)
		expect(res.body.message).toBe("success")
	})

	it(" get /api/longUrl", async () => {
		// await shortUrlMock.add(testData.exits.longUrl)
		const shortUrl = testData.exits.shortUrl
		const res = await request(app)
			.get("/api/longUrl")
			.query({ shortUrl })
			.send()

		expect(res.status).toBe(200)
		expect(res.body.shortUrl).toBeTruthy()
		expect(res.body.longUrl).toBe(testData.exits.longUrl)
		expect(res.body.message).toBe("success")
		const res2 = await request(app)
			.get("/api/longUrl")
			.query({ shortUrl: "1122" })
			.send()
		expect(res2.status).toBe(200)
		expect(res2.body.error).toBe(400)
		expect(res2.body.message).toBe("链接不合法")
	})

	it(" get exist /api/longUrl", async () => {
		await shortUrlMock.add(testData.exits.longUrl)
		const shortUrl = testData.exits.shortUrl
		const res = await request(app)
			.get("/api/longUrl")
			.query({ shortUrl })
			.send()

		expect(res.status).toBe(200)
		expect(res.body.shortUrl).toBeTruthy()
		expect(res.body.longUrl).toBe(testData.exits.longUrl)
		expect(res.body.message).toBe("success")
		await shortUrlMock.delete(testData.exits.longUrl)
	})

	it(" get not exist /api/longUrl", async () => {
		const res2 = await request(app).get("/api/longUrl").send()
		expect(res2.body.error).toBe(400)

		const shortUrl = testData.notExits.shortUrl
		const res = await request(app)
			.get("/api/longUrl")
			.query({ shortUrl })
			.send()

		expect(res2.body.error).toBe(400)
		expect(res.body.message).toBe("获取短链接失败")
	})
})
