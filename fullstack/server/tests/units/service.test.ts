import '../../src/types.d'
import { createMemoryRepository } from '../../src/repository/repository_memory'
import { createService } from '../../src/service'


describe('service', () => {
  it('createOrQueryShort', async () => {
    var service = createService(createMemoryRepository())
  })
})