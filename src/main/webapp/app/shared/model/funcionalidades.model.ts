import { IPerfiles } from '@/shared/model/perfiles.model';

import { Funcionalidad } from '@/shared/model/enumerations/funcionalidad.model';
export interface IFuncionalidades {
  id?: number;
  fecha?: Date;
  perfil?: string;
  funcionalidad?: Funcionalidad | null;
  alta?: string | null;
  editar?: string | null;
  activarInact?: string | null;
  consulta?: string | null;
  usuario?: string | null;
  fechaMod?: Date | null;
  perfils?: IPerfiles[] | null;
}

export class Funcionalidades implements IFuncionalidades {
  constructor(
    public id?: number,
    public fecha?: Date,
    public perfil?: string,
    public funcionalidad?: Funcionalidad | null,
    public alta?: string | null,
    public editar?: string | null,
    public activarInact?: string | null,
    public consulta?: string | null,
    public usuario?: string | null,
    public fechaMod?: Date | null,
    public perfils?: IPerfiles[] | null
  ) {}
}
