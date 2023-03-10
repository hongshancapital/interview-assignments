import '../../src/types.d'
import { createMemoryRepository } from '../../src/repository'


// async function testRepository(repo: UrlRepository) {


//   console.log(urlData)
//   expect(urlData.id).toBeInstanceOf(Number)
//   expect(urlData.url).toEqual(url)
//   expect(urlData.short).not.toBeUndefined()
//   expect(urlData.id).toBeInstanceOf(Number)
//   expect(urlData.id).toBeInstanceOf(Number)

//   let urlDataAgain = repo.queryByUrl(url)
//   expect(JSON.stringify(urlDataAgain)).toEqual(JSON.stringify(urlData));
// }

function createUnits(repoType: string, repositoryFactory: () => UrlRepository) {
  it(`${repoType} createId`, async () => {
    let repo = repositoryFactory();
    let id = await repo.createId()
    let id2 = await repo.createId()
    let id3 = await repo.createId()
    expect(id).toBe(0)
    expect(id2).toBe(1)
    expect(id3).toBe(2)
  })

  it(`${repoType}queryByUrl`, async () => {
    let repo = repositoryFactory();
    let url = "http://example.com/abc"
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