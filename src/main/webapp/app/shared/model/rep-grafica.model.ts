import { Estatus } from '@/shared/model/enumerations/estatus.model';
export interface IRepGrafica {
  id?: number;
  fecha?: Date | null;
  repGrafica?: string | null;
  estatus?: Estatus | null;
  usuario?: string | null;
  fechaMod?: Date | null;
}

export class RepGrafica implements IRepGrafica {
  constructor(
    public id?: number,
    public fecha?: Date | null,
    public repGrafica?: string | null,
    public estatus?: Estatus | null,
    public usuario?: string | null,
    public fechaMod?: Date | null
  ) {}
}
