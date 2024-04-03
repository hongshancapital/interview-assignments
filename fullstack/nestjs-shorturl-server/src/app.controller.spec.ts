import { Test } from '@nestjs/testing'
import { AppController } from './app.controller'

describe('AppController', () => {
  let appController: AppController

  beforeEach(async () => {
    const moduleRef = await Test.createTestingModule({
      controllers: [AppController]
    }).compile()

    appController = moduleRef.get<AppController>(AppController)
  })

  it('should be define', () => {
    expect(appController).toBeDefined()
  })
})
