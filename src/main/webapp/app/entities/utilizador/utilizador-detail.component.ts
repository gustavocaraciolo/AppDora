import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUtilizador } from 'app/shared/model/utilizador.model';

@Component({
  selector: 'jhi-utilizador-detail',
  templateUrl: './utilizador-detail.component.html'
})
export class UtilizadorDetailComponent implements OnInit {
  utilizador: IUtilizador;

  constructor(private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ utilizador }) => {
      this.utilizador = utilizador;
    });
  }

  previousState() {
    window.history.back();
  }
}
