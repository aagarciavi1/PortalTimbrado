import { Estatus } from '@/shared/model/enumerations/estatus.model';
export interface IMotivosCancel {
  id?: number;
  fecha?: Date | null;
  motivoCancel?: string | null;
  estatus?: Estatus | null;
  usuario?: string | null;
  fechaMod?: Date | null;
}

export class MotivosCancel implements IMotivosCancel {
  constructor(
    public id?: number,
    public fecha?: Date | null,
    public motivoCancel?: string | null,
    public estatus?: Estatus | null,
    public usuario?: string | null,
    public fechaMod?: Date | null
  ) {}
}
