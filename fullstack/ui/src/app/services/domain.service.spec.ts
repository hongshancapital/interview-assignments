import { TestBed } from '@angular/core/testing';

import { DomainService } from './domain.service';

describe('DomainService', () => {
  let service: DomainService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DomainService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
