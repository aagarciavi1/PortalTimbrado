import { IRegimenFis } from '@/shared/model/regimen-fis.model';
import { IEstado } from '@/shared/model/estado.model';

import { Estatus } from '@/shared/model/enumerations/estatus.model';
export interface IRegistInstit {
  id?: number;
  fecha?: Date;
  idInstit?: number;
  estatus?: Estatus | null;
  nivel?: number;
  razonSocial?: string;
  rFC?: string;
  pais?: string | null;
  municipio?: string | null;
  localidad?: string | null;
  codigoPostal?: number | null;
  colonia?: string | null;
  calle?: string | null;
  numExt?: number | null;
  numInt?: number | null;
  logoContentType?: string | null;
  logo?: string | null;
  certificadoContentType?: string | null;
  certificado?: string | null;
  llavePrivContentType?: string | null;
  llavePriv?: string | null;
  contrasena?: string | null;
  fechaExp?: Date | null;
  dias?: number | null;
  correo?: string | null;
  usuario?: string | null;
  fechaMod?: Date | null;
  regimenfis?: IRegimenFis | null;
  estado?: IEstado | null;
  registinstit?: IRegistInstit | null;
  registinstiti?: IRegistInstit | null;
}

export class RegistInstit implements IRegistInstit {
  constructor(
    public id?: number,
    public fecha?: Date,
    public idInstit?: number,
    public estatus?: Estatus | null,
    public nivel?: number,
    public razonSocial?: string,
    public rFC?: string,
    public pais?: string | null,
    public municipio?: string | null,
    public localidad?: string | null,
    public codigoPostal?: number | null,
    public colonia?: string | null,
    public calle?: string | null,
    public numExt?: number | null,
    public numInt?: number | null,
    public logoContentType?: string | null,
    public logo?: string | null,
    public certificadoContentType?: string | null,
    public certificado?: string | null,
    public llavePrivContentType?: string | null,
    public llavePriv?: string | null,
    public contrasena?: string | null,
    public fechaExp?: Date | null,
    public dias?: number | null,
    public correo?: string | null,
    public usuario?: string | null,
    public fechaMod?: Date | null,
    public regimenfis?: IRegimenFis | null,
    public estado?: IEstado | null,
    public registinstit?: IRegistInstit | null,
    public registinstiti?: IRegistInstit | null
  ) {}
}
