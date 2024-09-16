import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { SERVICE_URL } from '../config';
import { DomainResult } from '../entity/domain';


@Injectable({
  providedIn: 'root'
})
export class DomainService {

  constructor(private http: HttpClient) { }

  getUrl(domain: string): Observable<DomainResult> {
    return this.http.post<DomainResult>(`${SERVICE_URL}/api/domain/domain_to_url`, { domain });
  }
  getDomain(url: string): Observable<DomainResult> {
    return this.http.get<DomainResult>(`${SERVICE_URL}/api/domain/domain_to_url/${url}`);
  }
}
