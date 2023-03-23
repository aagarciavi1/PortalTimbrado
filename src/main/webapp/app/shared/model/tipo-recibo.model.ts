import { Estatus } from '@/shared/model/enumerations/estatus.model';
export interface ITipoRecibo {
  id?: number;
  fecha?: Date | null;
  tipoRecibo?: string | null;
  clave?: string | null;
  estatus?: Estatus | null;
  usuario?: string | null;
  fechaMod?: Date | null;
}

export class TipoRecibo implements ITipoRecibo {
  constructor(
    public id?: number,
    public fecha?: Date | null,
    public tipoRecibo?: string | null,
    public clave?: string | null,
    public estatus?: Estatus | null,
    public usuario?: string | null,
    public fechaMod?: Date | null
  ) {}
}
