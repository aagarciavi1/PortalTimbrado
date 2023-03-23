import { IRegistInstit } from '@/shared/model/regist-instit.model';
import { ITipoCFDI } from '@/shared/model/tipo-cfdi.model';
import { ITipoRecibo } from '@/shared/model/tipo-recibo.model';

export interface IFTP {
  id?: number;
  fecha?: Date;
  idInstit?: number;
  nivel?: number;
  clave?: string | null;
  carpetaFTP?: string | null;
  subcarpetaFTP?: string | null;
  descripcionFTP?: string | null;
  ipFTP?: string | null;
  puerto?: number | null;
  usuarioFTP?: string | null;
  contrasena?: string | null;
  usuario?: string | null;
  fechaMod?: Date | null;
  registinstit?: IRegistInstit | null;
  tipocfdi?: ITipoCFDI | null;
  tiporecibo?: ITipoRecibo | null;
}

export class FTP implements IFTP {
  constructor(
    public id?: number,
    public fecha?: Date,
    public idInstit?: number,
    public nivel?: number,
    public clave?: string | null,
    public carpetaFTP?: string | null,
    public subcarpetaFTP?: string | null,
    public descripcionFTP?: string | null,
    public ipFTP?: string | null,
    public puerto?: number | null,
    public usuarioFTP?: string | null,
    public contrasena?: string | null,
    public usuario?: string | null,
    public fechaMod?: Date | null,
    public registinstit?: IRegistInstit | null,
    public tipocfdi?: ITipoCFDI | null,
    public tiporecibo?: ITipoRecibo | null
  ) {}
}
