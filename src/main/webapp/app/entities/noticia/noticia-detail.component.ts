import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INoticia } from 'app/shared/model/noticia.model';

@Component({
  selector: 'jhi-noticia-detail',
  templateUrl: './noticia-detail.component.html'
})
export class NoticiaDetailComponent implements OnInit {
  noticia: INoticia;

  constructor(private activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ noticia }) => {
      this.noticia = noticia;
    });
  }

  previousState() {
    window.history.back();
  }
}
