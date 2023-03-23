import { IRegistInstit } from '@/shared/model/regist-instit.model';
import { IPerfiles } from '@/shared/model/perfiles.model';
import { ITipoCFDI } from '@/shared/model/tipo-cfdi.model';
import { ITipoRecibo } from '@/shared/model/tipo-recibo.model';

import { Estatus } from '@/shared/model/enumerations/estatus.model';
export interface IUsuarios {
  id?: number;
  fecha?: Date;
  usuario?: string;
  descr?: string;
  idInstit?: number;
  correo?: string | null;
  contrasena?: string | null;
  confirmarContras?: string | null;
  estatus?: Estatus | null;
  usuarioCrea?: string | null;
  registinstit?: IRegistInstit | null;
  perfil?: IPerfiles | null;
  tipocfdi?: ITipoCFDI | null;
  tiporecibo?: ITipoRecibo | null;
}

export class Usuarios implements IUsuarios {
  constructor(
    public id?: number,
    public fecha?: Date,
    public usuario?: string,
    public descr?: string,
    public idInstit?: number,
    public correo?: string | null,
    public contrasena?: string | null,
    public confirmarContras?: string | null,
    public estatus?: Estatus | null,
    public usuarioCrea?: string | null,
    public registinstit?: IRegistInstit | null,
    public perfil?: IPerfiles | null,
    public tipocfdi?: ITipoCFDI | null,
    public tiporecibo?: ITipoRecibo | null
  ) {}
}
