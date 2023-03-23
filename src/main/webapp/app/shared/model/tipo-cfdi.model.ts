import { Estatus } from '@/shared/model/enumerations/estatus.model';
export interface ITipoCFDI {
  id?: number;
  fecha?: Date | null;
  tipoCFDI?: string | null;
  estatus?: Estatus | null;
  usuario?: string | null;
  fechaMod?: Date | null;
}

export class TipoCFDI implements ITipoCFDI {
  constructor(
    public id?: number,
    public fecha?: Date | null,
    public tipoCFDI?: string | null,
    public estatus?: Estatus | null,
    public usuario?: string | null,
    public fechaMod?: Date | null
  ) {}
}
