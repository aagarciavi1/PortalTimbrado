import { Estatus } from '@/shared/model/enumerations/estatus.model';
export interface IEstatusCFDI {
  id?: number;
  fecha?: Date | null;
  descEstCFDI?: string | null;
  estatus?: Estatus | null;
  usuario?: string | null;
  fechaMod?: Date | null;
}

export class EstatusCFDI implements IEstatusCFDI {
  constructor(
    public id?: number,
    public fecha?: Date | null,
    public descEstCFDI?: string | null,
    public estatus?: Estatus | null,
    public usuario?: string | null,
    public fechaMod?: Date | null
  ) {}
}
