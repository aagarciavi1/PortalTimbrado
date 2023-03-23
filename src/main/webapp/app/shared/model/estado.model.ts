import { Estatus } from '@/shared/model/enumerations/estatus.model';
export interface IEstado {
  id?: number;
  fecha?: Date | null;
  pais?: string | null;
  estatdo?: string | null;
  descEstado?: string | null;
  estatus?: Estatus | null;
  usuario?: string | null;
  fechaMod?: Date | null;
}

export class Estado implements IEstado {
  constructor(
    public id?: number,
    public fecha?: Date | null,
    public pais?: string | null,
    public estatdo?: string | null,
    public descEstado?: string | null,
    public estatus?: Estatus | null,
    public usuario?: string | null,
    public fechaMod?: Date | null
  ) {}
}
