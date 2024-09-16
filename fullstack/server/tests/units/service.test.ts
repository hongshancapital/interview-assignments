import '../../src/types.d'
import { createMemoryRepository } from '../../src/repository/repository_memory'
import { createService } from '../../src/service'


describe('service', () => {
  it('createOrQueryShort', async () => {
    const service = createService(await createMemoryRepository())
    const url = "https://example.com/abc";
    const urlData = await service.createOrQueryShort(url)
    expect(urlData.id).not.toBeUndefined()
    expect(urlData.url).toEqual(url)
    expect(urlData.short.length).toEqual(8)
    expect(urlData.createTime).not.toBeUndefined()
    expect(urlData.refreshTime).not.toBeUndefined()
  })

  it('queryByShort', async () => {
    const service = createService(await createMemoryRepository())
    const url = "https://example.com/abc";
    const urlData = await service.createOrQueryShort(url)
    const urlData2 = await service.queryByShort(urlData.short)
    expect(urlData2).toMatchObject(urlData)

  })
})