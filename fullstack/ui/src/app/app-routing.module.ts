import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DomainComponent } from './pages/domain/domain.component';
import { IndexComponent } from './pages/index/index.component';

const routes: Routes = [
  { path: "", component: IndexComponent },
  { path: "domain", component: DomainComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
