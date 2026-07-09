import {connectDB} from "../../config/db";

describe("DB", () => {
    it("connect", async () => {
        const ret =  await connectDB();
        expect(ret).toBe(true);
    });
});
