import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUtilizador } from 'app/shared/model/utilizador.model';
import { UtilizadorService } from './utilizador.service';

@Component({
  selector: 'jhi-utilizador-delete-dialog',
  templateUrl: './utilizador-delete-dialog.component.html'
})
export class UtilizadorDeleteDialogComponent {
  utilizador: IUtilizador;

  constructor(private utilizadorService: UtilizadorService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.utilizadorService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'utilizadorListModification',
        content: 'Deleted an utilizador'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-utilizador-delete-popup',
  template: ''
})
export class UtilizadorDeletePopupComponent implements OnInit, OnDestroy {
  private ngbModalRef: NgbModalRef;

  constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ utilizador }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(UtilizadorDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.utilizador = utilizador;
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
