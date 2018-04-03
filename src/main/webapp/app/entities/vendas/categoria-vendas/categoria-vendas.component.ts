import { Component, OnInit, Input } from '@angular/core';
import {trigger, state, style, transition, animate} from '@angular/animations';
import {Categoria} from "../../categoria";

@Component({
  selector: 'jhi-categoria-vendas',
  templateUrl: './categoria-vendas.component.html',
    animations: [
        trigger('categoriaAppeared', [
            state('ready', style({opacity: 1})),
            transition('void => ready', [
                style({opacity: 0, transform: 'translate(-30px, -10px)'}),
                animate('300ms 0s ease-in-out')
            ])
        ])
    ]
})
export class CategoriaVendasComponent implements OnInit {

    categoriaState = 'ready'

    @Input() categoria: Categoria

    constructor() { }

    ngOnInit() {
    }

}
