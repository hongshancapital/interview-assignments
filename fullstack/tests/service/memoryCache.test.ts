import MemoryCache from "../../src/service/memoryCache";

describe("memeoryCache", () => {
  it("singleton",  () => {
    const m = MemoryCache.getInstance();
    expect(m).toBe(MemoryCache.getInstance());
    expect(m).not.toBe(new MemoryCache());
  });

  it("get set", () => {
    MemoryCache.getInstance().setValue("test", "1");
    expect(MemoryCache.getInstance().getValue("test")).toBe("1");
  });

  it("get set - time expired", () => {
    MemoryCache.getInstance().setValue("test", "1", -1);
    expect(MemoryCache.getInstance().getValue("test")).toBe(undefined);
  });

  it("get set - undefined to remove", () => {
    MemoryCache.getInstance().setValue("test", "1");
    expect(MemoryCache.getInstance().getValue("test")).toBe("1");
    MemoryCache.getInstance().setValue("test", undefined);
    expect(MemoryCache.getInstance().getValue("test")).toBe(undefined);
  });
});