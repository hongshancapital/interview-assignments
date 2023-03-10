import '../../src/types.d'
import { createMemoryRepository } from '../../src/repository'
import { createService } from '../../src/service'


describe('service', () => {
  it('createOrQueryShort', async () => {
    var service = createService(createMemoryRepository())
  })
})