import { IRegistInstit } from '@/shared/model/regist-instit.model';
import { ITipoCFDI } from '@/shared/model/tipo-cfdi.model';
import { ITipoRecibo } from '@/shared/model/tipo-recibo.model';

import { TipoNot } from '@/shared/model/enumerations/tipo-not.model';
export interface INotificaciones {
  id?: number;
  fecha?: Date;
  idInstit?: number;
  nivel?: number;
  tipoNot?: TipoNot | null;
  clave?: string | null;
  asunto?: string | null;
  texto?: number | null;
  piePagina?: string | null;
  usuario?: string | null;
  fechaMod?: Date | null;
  registinstit?: IRegistInstit | null;
  tipocfdi?: ITipoCFDI | null;
  tiporecibo?: ITipoRecibo | null;
}

export class Notificaciones implements INotificaciones {
  constructor(
    public id?: number,
    public fecha?: Date,
    public idInstit?: number,
    public nivel?: number,
    public tipoNot?: TipoNot | null,
    public clave?: string | null,
    public asunto?: string | null,
    public texto?: number | null,
    public piePagina?: string | null,
    public usuario?: string | null,
    public fechaMod?: Date | null,
    public registinstit?: IRegistInstit | null,
    public tipocfdi?: ITipoCFDI | null,
    public tiporecibo?: ITipoRecibo | null
  ) {}
}
