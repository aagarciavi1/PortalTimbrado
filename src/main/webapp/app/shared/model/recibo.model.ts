import { ITipoRecibo } from '@/shared/model/tipo-recibo.model';
import { IRepGrafica } from '@/shared/model/rep-grafica.model';
import { IParametros } from '@/shared/model/parametros.model';

export interface IRecibo {
  id?: number;
  fecha?: Date;
  idInstit?: number;
  nivel?: number;
  tipoCFDI?: string;
  clave?: string | null;
  envioConCFDI?: string | null;
  envioSinCFDI?: string | null;
  aplicaLeyenda?: string | null;
  leyenda?: number | null;
  usuario?: string | null;
  fechaMod?: Date | null;
  tiporecibo?: ITipoRecibo | null;
  repgrafica?: IRepGrafica | null;
  parametros?: IParametros | null;
}

export class Recibo implements IRecibo {
  constructor(
    public id?: number,
    public fecha?: Date,
    public idInstit?: number,
    public nivel?: number,
    public tipoCFDI?: string,
    public clave?: string | null,
    public envioConCFDI?: string | null,
    public envioSinCFDI?: string | null,
    public aplicaLeyenda?: string | null,
    public leyenda?: number | null,
    public usuario?: string | null,
    public fechaMod?: Date | null,
    public tiporecibo?: ITipoRecibo | null,
    public repgrafica?: IRepGrafica | null,
    public parametros?: IParametros | null
  ) {}
}
