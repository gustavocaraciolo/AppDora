import { Moment } from 'moment';
import { IUtilizador } from 'app/shared/model//utilizador.model';

export const enum FlagSimNao {
  SIM = 'SIM',
  NAO = 'NAO'
}

export interface IPortal {
  id?: number;
  descricao?: string;
  dataAtivacao?: Moment;
  flagDefault?: FlagSimNao;
  tipoIdioma?: string;
  flagSenhaEncriptada?: FlagSimNao;
  utilizdors?: IUtilizador[];
}

export class Portal implements IPortal {
  constructor(
    public id?: number,
    public descricao?: string,
    public dataAtivacao?: Moment,
    public flagDefault?: FlagSimNao,
    public tipoIdioma?: string,
    public flagSenhaEncriptada?: FlagSimNao,
    public utilizdors?: IUtilizador[]
  ) {}
}
