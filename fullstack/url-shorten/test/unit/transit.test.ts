import { url2Base62 } from "../../src/application/transit"

describe("url2Base62 unit tests", () => {
    it("Should return string 'kd9' if the 0x10 is correctly be converted to 0x62", () => {
        expect(url2Base62(85001)).toEqual("kd9")
    })
})
