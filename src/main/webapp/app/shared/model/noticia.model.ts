import { Moment } from 'moment';
import { IPortal } from 'app/shared/model//portal.model';

export interface INoticia {
  id?: number;
  descricao?: string;
  dataInicio?: Moment;
  dataFim?: Moment;
  portal?: IPortal;
}

export class Noticia implements INoticia {
  constructor(
    public id?: number,
    public descricao?: string,
    public dataInicio?: Moment,
    public dataFim?: Moment,
    public portal?: IPortal
  ) {}
}
