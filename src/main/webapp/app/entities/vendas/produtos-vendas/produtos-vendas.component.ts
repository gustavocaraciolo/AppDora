import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {Produto, ProdutoService} from "../../produto";
import {HttpResponse} from "@angular/common/http";

@Component({
  selector: 'jhi-produtos-vendas',
  templateUrl: './produtos-vendas.component.html',
  styles: []
})
export class ProdutosVendasComponent implements OnInit {

    produto: Produto

    constructor(private produtoService: ProdutoService,
                private route: ActivatedRoute) {
    }

    ngOnInit() {
        this.produtoService.find(this.route.snapshot.params['id'])
            .subscribe((produtoResponse: HttpResponse<Produto>) => {
            this.produto = produtoResponse.body;
        });
    }
}
