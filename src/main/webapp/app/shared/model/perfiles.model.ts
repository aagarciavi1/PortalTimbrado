import { IFuncionalidades } from '@/shared/model/funcionalidades.model';

export interface IPerfiles {
  id?: number;
  fecha?: Date;
  perfil?: string;
  usuario?: string | null;
  fechaMod?: Date | null;
  funcionalidades?: IFuncionalidades | null;
}

export class Perfiles implements IPerfiles {
  constructor(
    public id?: number,
    public fecha?: Date,
    public perfil?: string,
    public usuario?: string | null,
    public fechaMod?: Date | null,
    public funcionalidades?: IFuncionalidades | null
  ) {}
}
