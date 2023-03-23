import { IRegistInstit } from '@/shared/model/regist-instit.model';
import { ITipoCFDI } from '@/shared/model/tipo-cfdi.model';
import { IRecibo } from '@/shared/model/recibo.model';

export interface IParametros {
  id?: number;
  fecha?: Date;
  idInstit?: number;
  nivel?: number;
  usuario?: string | null;
  fechaMod?: Date | null;
  registinstit?: IRegistInstit | null;
  tipocfdi?: ITipoCFDI | null;
  recibos?: IRecibo[] | null;
}

export class Parametros implements IParametros {
  constructor(
    public id?: number,
    public fecha?: Date,
    public idInstit?: number,
    public nivel?: number,
    public usuario?: string | null,
    public fechaMod?: Date | null,
    public registinstit?: IRegistInstit | null,
    public tipocfdi?: ITipoCFDI | null,
    public recibos?: IRecibo[] | null
  ) {}
}
