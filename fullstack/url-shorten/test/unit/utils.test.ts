import { isValidWS, isValidShortUrl } from "../../src/lib/utils"

describe("isValidWS unit tests", () => {
    it("Should return true if the original website is valid", () => {
        const longUrl = "https://google.com/test"
        expect(isValidWS(longUrl)).toBe(true)
    })

    it("Should return false if the original website is not beginning with letter", () => {
        const longUrl = "1https://google.com/test"
        expect(isValidWS(longUrl)).toBe(false)
    })
})

describe("isValidShortUrl unit tests", () => {
    it("Should return true if the shortUrl is valid", () => {
        const shortUrl = "AE0l"
        expect(isValidShortUrl(shortUrl)).toBe(true)
    })

    it("Should return false if the shortUrl is longer than 8 bit", () => {
        const shortUrl = "kW891Wq1190errrr"
        expect(isValidShortUrl(shortUrl)).toBe(false)
    })

    it("Should return false if the shortUrl contains character that is not in [a-z A-Z 0-9]", () => {
        const shortUrl = "kW-"
        expect(isValidShortUrl(shortUrl)).toBe(false)
    })
})
