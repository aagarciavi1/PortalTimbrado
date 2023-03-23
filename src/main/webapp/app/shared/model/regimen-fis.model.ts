import { Estatus } from '@/shared/model/enumerations/estatus.model';
export interface IRegimenFis {
  id?: number;
  fecha?: Date | null;
  regimenF?: string | null;
  estatus?: Estatus | null;
  usuario?: string | null;
  fechaMod?: string | null;
}

export class RegimenFis implements IRegimenFis {
  constructor(
    public id?: number,
    public fecha?: Date | null,
    public regimenF?: string | null,
    public estatus?: Estatus | null,
    public usuario?: string | null,
    public fechaMod?: string | null
  ) {}
}
