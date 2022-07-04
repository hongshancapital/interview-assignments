import { Test, TestingModule } from '@nestjs/testing';
import { AppModule } from './app.module';

describe('AppModule', () => {
  let appModule: AppModule;

  beforeEach(async () => {
    const module: TestingModule = await Test.createTestingModule({
      providers: [AppModule],
    }).compile();

    appModule = module.get<AppModule>(AppModule);
  });

  it('should be defined', () => {
    expect(appModule).toBeDefined();
  });
});
