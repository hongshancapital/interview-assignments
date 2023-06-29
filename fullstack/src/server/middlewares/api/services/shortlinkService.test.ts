import { describe, expect, it, jest } from "@jest/globals";
import {
  Base62Encode,
  Base62Decode,
  getOriginLinkById,
} from "./shortlinkService.js";

const mockPoolGetConnection = jest.fn();
jest.unstable_mockModule("../../../db.js", () => {
  return {
    pool: {
      getConnection: mockPoolGetConnection,
    },
  };
});

describe("shortlink service and utils", () => {
  it("Base62 encode", () => {
    const encodeStr1 = Base62Encode(100000n);
    const encodeStr2 = Base62Encode(10n);
    const encodeStr3 = Base62Encode(9999999999999999n);

    expect(encodeStr1).toEqual("q0U");
    expect(encodeStr2).toEqual("a");
    expect(encodeStr3).toEqual("JNBBGsgR9");
  });

  it("Base62 decode", () => {
    const decodeStr1 = Base62Decode("q0U");
    const decodeStr2 = Base62Decode("a");
    const decodeStr3 = Base62Decode("JNBBGsgR9");

    expect(decodeStr1).toEqual(100000n);
    expect(decodeStr2).toEqual(10n);
    expect(decodeStr3).toEqual(9999999999999999n);
  });

  it("get OriginLink by Id with more than 8 characters short link id", () => {
    return expect(getOriginLinkById("123456789")).rejects.toBe(false);
  });
});
