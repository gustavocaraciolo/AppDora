import { IUser } from 'app/core/user/user.model';
import { IPortal } from 'app/shared/model//portal.model';

export const enum Genero {
  MASCULINO = 'MASCULINO',
  FEMININO = 'FEMININO',
  OUTROS = 'OUTROS'
}

export interface IUtilizador {
  id?: number;
  primeiroNome?: string;
  ultimoNome?: string;
  genero?: Genero;
  email?: string;
  telefone?: string;
  enderecoLinha1?: string;
  enderecoLinha2?: string;
  cidade?: string;
  pais?: string;
  user?: IUser;
  portals?: IPortal[];
}

export class Utilizador implements IUtilizador {
  constructor(
    public id?: number,
    public primeiroNome?: string,
    public ultimoNome?: string,
    public genero?: Genero,
    public email?: string,
    public telefone?: string,
    public enderecoLinha1?: string,
    public enderecoLinha2?: string,
    public cidade?: string,
    public pais?: string,
    public user?: IUser,
    public portals?: IPortal[]
  ) {}
}
