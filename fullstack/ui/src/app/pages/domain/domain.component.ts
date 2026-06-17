import { Component, OnInit } from '@angular/core';
import { DomainService } from 'src/app/services/domain.service';

@Component({
  selector: 'app-domain',
  templateUrl: './domain.component.html',
  styleUrls: ['./domain.component.scss']
})
export class DomainComponent implements OnInit {

  constructor(private domainService: DomainService) { }

  ngOnInit(): void {
  }

}
