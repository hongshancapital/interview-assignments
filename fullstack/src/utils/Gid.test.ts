import { Gid } from "./Gid";

describe("Gid works as expected", () => {
  let GeneratorA = new Gid({
    machineId: 0,
    instanceId: 1,
  });
  let GeneratorB = new Gid({
    machineId: 1,
    instanceId: 2,
  });
  let GeneratorC = new Gid({
    machineId: 0,
    instanceId: 2,
  });

  it("should default config work", () => {
    expect(new Gid().generate().match(/^[0-9]{3}/g)).toEqual(["000"]);
  });

  it("should default config work", () => {
    expect(() => {
      // @ts-ignore
      new Gid({ machineId: 4 });
    }).toThrow();
    expect(() => {
      // @ts-ignore
      new Gid({ machineId: -1 });
    }).toThrow();
  });

  it("should throw an error if instanceId > 4 or < 0", () => {
    expect(() => {
      // @ts-ignore
      new Gid({ instanceId: 4 });
    }).toThrow();
    expect(() => {
      // @ts-ignore
      new Gid({ instanceId: -1 });
    }).toThrow();
  });

  it("machineId should work", () => {
    expect(GeneratorA.generate()).not.toEqual(GeneratorB.generate());
  });

  it("instanceId should work", () => {
    expect(GeneratorA.generate()).not.toEqual(GeneratorC.generate());
  });

  it("single instance should work", () => {
    expect(GeneratorA.generate()).not.toEqual(GeneratorA.generate());
  });

  it("QPS 1w should work", () => {
    const ids: string[] = [];
    while (ids.length < 11000) {
      ids.push(GeneratorA.generate());
    }
    expect(Array.from(new Set(ids)).length).toEqual(11000);
  });

  it("should counter max work", () => {
    const GeneratorD = new Gid();
    const ids: string[] = [];
    while (ids.length < 16) {
      ids.push(GeneratorD.generate());
    }
    // 1bit 2bit 41bit 4bit
    expect(ids[14].slice(3, 44)).toEqual(ids[13].slice(3, 44));
    expect(ids[14].slice(3, 44)).not.toEqual(ids[15].slice(3, 44));
  });

  it("fixLen should work", () => {
    expect(GeneratorA.fixLen("AA", 4)).toEqual("00AA");
    expect(GeneratorA.fixLen("AAAA", 4)).toEqual("AAAA");
    expect(GeneratorA.fixLen("AAAAA", 4)).toEqual("AAAAA");
  });

  it("bitToBase64 should work", () => {
    expect(
      GeneratorA.bitToBase64("001110000110101011000000100111111001010011001101")
    ).toEqual("E6h0dvJD");
    expect(
      GeneratorA.bitToBase64(
        "0001110000110101011000000100111111001010011001101"
      )
    ).toEqual("73LWJyfc");
    expect(GeneratorA.bitToBase64("0")).toEqual("00000000");
  });
});
