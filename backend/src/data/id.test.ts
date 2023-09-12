import { ErrorIdMaxExceed, ErrorIdSystemNotCreated } from "../biz/errors/id";
import IdModel, { SystemNameId } from "./models/id";
import { encodeBase58 } from "../util/base58";
import { maxId, newId } from "./id";

describe('newId', () => {
  jest.mock('../models/id'); // 使用 Jest 模拟 IdModel
  it('should generate a new ID and encode it', async () => {
    // 使用 jest.fn() 创建一个模拟的 findOneAndUpdate 函数
    const findOneAndUpdateMock = jest.fn(() => ({
      exec: jest.fn().mockResolvedValue({ id: BigInt(42) }) // 模拟 exec() 方法
    }));
    (IdModel.findOneAndUpdate as jest.Mock) = findOneAndUpdateMock;

    const result = await newId();

    expect(IdModel.findOneAndUpdate).toHaveBeenCalledWith(
      { name: SystemNameId },
      { "$inc": { id: 1 } },
      { new: true, fields: "id", upsert: true }
    );
    expect(result).toBe(encodeBase58(BigInt(42)));
  });

  it('should handle IdModel.findOneAndUpdate returning null', async () => {
    // 使用 jest.fn() 创建一个模拟的 findOneAndUpdate 函数
    const findOneAndUpdateMock = jest.fn(() => ({
      exec: jest.fn().mockResolvedValue(null) // 模拟 exec() 方法
    }));
    (IdModel.findOneAndUpdate as jest.Mock) = findOneAndUpdateMock;

    await expect(newId()).rejects.toThrow(ErrorIdSystemNotCreated);
  });

  it('should handle IdModel.id exceeding maxId', async () => {
    // 使用 jest.fn() 创建一个模拟的 findOneAndUpdate 函数
    const findOneAndUpdateMock = jest.fn(() => ({
      exec: jest.fn().mockResolvedValue({ id: maxId + BigInt(1) }) // 模拟 exec() 方法
    }));
    (IdModel.findOneAndUpdate as jest.Mock) = findOneAndUpdateMock;

    await expect(newId()).rejects.toThrow(ErrorIdMaxExceed);
  });
  jest.unmock('../models/id');
});
