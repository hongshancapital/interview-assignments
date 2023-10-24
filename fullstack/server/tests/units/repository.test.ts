import '../../src/types.d'
import { createMemoryRepository } from '../../src/repository/repository_memory'
import { createSqlRepository } from '../../src/repository/repository_sql'
import { createRedisRepository } from '../../src/repository/repository_redis'
import { initSqlConnection } from '../../src/db/sqlite'
import { initRedisConnnection } from '../../src/db/redis'

function createUnits(repoType: string, repositoryFactory: () => Promise<UrlRepository>) {
  it(`${repoType} createId`, async () => {
    let repo = await repositoryFactory();
    let id = await repo.createId()
    let id2 = await repo.createId()
    let id3 = await repo.createId()
    expect(id2).toEqual(id + 1)
    expect(id3).toEqual(id + 2)
  })

  it(`${repoType} queryByUrl`, async () => {
    let repo = await repositoryFactory();
    let url = "http://example.com/xxxx"
    let urlData = await repo.queryByUrl(url)
    expect(urlData).toBeUndefined()
  })
  
  it(`${repoType} save & queryByUrl`, async () => {
    let repo = await repositoryFactory();
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
    expect(urlData2).toMatchObject(urlData)
  })

  // it(`${repoType} save & queryByShort`, async () => {
  //   let repo = await repositoryFactory();
  //   let url = "http://example.com/abc"

  //   let urlData: UrlStoreData = {
  //     id: 0,
  //     url,
  //     short: 'abcdefgab',
  //     createTime: Date.now(),
  //     refreshTime: Date.now() + 1
  //   }
  //   await repo.save(urlData)
    
  //   let urlData2 = await repo.queryByShort(urlData.short)
  //   expect(urlData).not.toBeUndefined()
  //   expect(urlData2).toMatchObject(urlData)
  // })
}

describe('repository', () => {
  createUnits("memory", () => Promise.resolve(createMemoryRepository()))
  createUnits("sqlite", async () => {
    const db = await initSqlConnection();
    return createSqlRepository(db)
  })
  createUnits("redis", async () => {
    const client = await initRedisConnnection();
    return createRedisRepository(client)
  })
})