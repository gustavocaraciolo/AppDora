import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { INoticia } from 'app/shared/model/noticia.model';
import { NoticiaService } from './noticia.service';

@Component({
  selector: 'jhi-noticia-delete-dialog',
  templateUrl: './noticia-delete-dialog.component.html'
})
export class NoticiaDeleteDialogComponent {
  noticia: INoticia;

  constructor(private noticiaService: NoticiaService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.noticiaService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'noticiaListModification',
        content: 'Deleted an noticia'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-noticia-delete-popup',
  template: ''
})
export class NoticiaDeletePopupComponent implements OnInit, OnDestroy {
  private ngbModalRef: NgbModalRef;

  constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ noticia }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(NoticiaDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.noticia = noticia;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
