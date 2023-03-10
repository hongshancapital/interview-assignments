import '../../src/types.d'
import { createMemoryRepository } from '../../src/repository'

function createUnits(repoType: string, repositoryFactory: () => UrlRepository) {
  it(`${repoType} createId`, async () => {
    let repo = repositoryFactory();
    let id = await repo.createId()
    let id2 = await repo.createId()
    let id3 = await repo.createId()
    expect(id2).toEqual(id + 1)
    expect(id3).toEqual(id + 2)
  })

  it(`${repoType}queryByUrl`, async () => {
    let repo = repositoryFactory();
    let url = "http://example.com/xxxx"
    let urlData = await repo.queryByUrl(url)
    expect(urlData).toBeUndefined()
  })
  
  it(`${repoType}save & queryByUrl`, async () => {
    let repo = repositoryFactory();
    let url = "http://example.com/abc"

    let urlData: UrlStoreData = {
      id: 0,
      url,
      short: 'abcdefgab',
      createTime: Date.now(),
      refreshTime: Date.now() + 1
    }
    await repo.save(urlData)
    
    let urlData2 = await repo.queryByUrl(url)
    expect(urlData).not.toBeUndefined()
    expect(JSON.stringify(urlData2)).toEqual(JSON.stringify(urlData))
  })

  it(`${repoType}save & queryByShort`, async () => {
    let repo = repositoryFactory();
    let url = "http://example.com/abc"

    let urlData: UrlStoreData = {
      id: 0,
      url,
      short: 'abcdefgab',
      createTime: Date.now(),
      refreshTime: Date.now() + 1
    }
    await repo.save(urlData)
    
    let urlData2 = await repo.queryByShort(urlData.short)
    expect(urlData).not.toBeUndefined()
    expect(JSON.stringify(urlData2)).toEqual(JSON.stringify(urlData))
  })
}

describe('repository', () => {
  createUnits("memory", createMemoryRepository)
})