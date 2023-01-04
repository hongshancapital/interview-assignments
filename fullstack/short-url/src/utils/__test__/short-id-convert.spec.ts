import * as conv from '../short-id-converter'

describe('short id convert', () => {
  it('should return fixed uniq 8 length short id when encode give any int number <= 194754273880', async () => {
    const hashIdMin = conv.encode(0)
    const hashIdMax = conv.encode(194754273880)
    const hashIdOver = conv.encode(194754273881)

    expect(hashIdMin).toHaveLength(8)
    expect(hashIdMax).toHaveLength(8)

    expect(hashIdMin).toMatchSnapshot()
    expect(hashIdMax).toMatchSnapshot()

    expect(hashIdMin).not.toEqual(hashIdMax)
    expect(hashIdOver).toHaveLength(9)
  });

  it('should return original number id when decode give short id', async () => {
    const hashIdOne = conv.encode(3)
    const hashIdTwo = conv.encode(4)

    expect(conv.decode(hashIdOne)).toEqual(3)
    expect(conv.decode(hashIdTwo)).toEqual(4)
  });
});
