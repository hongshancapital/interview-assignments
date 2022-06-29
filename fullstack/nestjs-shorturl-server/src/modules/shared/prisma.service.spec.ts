import { Test } from '@nestjs/testing'
import { PrismaService } from './prisma.service'

describe('PrismaService', () => {
  let prismaService: PrismaService

  beforeEach(async () => {
    const moduleRef = await Test.createTestingModule({
      providers: [PrismaService]
    }).compile()

    prismaService = moduleRef.get<PrismaService>(PrismaService)
  })

  it('should be define', () => {
    expect(prismaService).toBeDefined()
  })
})
