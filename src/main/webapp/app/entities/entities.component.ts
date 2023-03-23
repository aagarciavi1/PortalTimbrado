import { Component, Provide, Vue } from 'vue-property-decorator';

import UserService from '@/entities/user/user.service';
import RegistInstitService from './regist-instit/regist-instit.service';
import ParametrosService from './parametros/parametros.service';
import ReciboService from './recibo/recibo.service';
import FTPService from './ftp/ftp.service';
import NotificacionesService from './notificaciones/notificaciones.service';
import PerfilesService from './perfiles/perfiles.service';
import FuncionalidadesService from './funcionalidades/funcionalidades.service';
import UsuariosService from './usuarios/usuarios.service';
import RegimenFisService from './regimen-fis/regimen-fis.service';
import EstadoService from './estado/estado.service';
import EstatusCFDIService from './estatus-cfdi/estatus-cfdi.service';
import MotivosCancelService from './motivos-cancel/motivos-cancel.service';
import TipoCFDIService from './tipo-cfdi/tipo-cfdi.service';
import TipoReciboService from './tipo-recibo/tipo-recibo.service';
import RepGraficaService from './rep-grafica/rep-grafica.service';
// jhipster-needle-add-entity-service-to-entities-component-import - JHipster will import entities services here

@Component
export default class Entities extends Vue {
  @Provide('userService') private userService = () => new UserService();
  @Provide('registInstitService') private registInstitService = () => new RegistInstitService();
  @Provide('parametrosService') private parametrosService = () => new ParametrosService();
  @Provide('reciboService') private reciboService = () => new ReciboService();
  @Provide('fTPService') private fTPService = () => new FTPService();
  @Provide('notificacionesService') private notificacionesService = () => new NotificacionesService();
  @Provide('perfilesService') private perfilesService = () => new PerfilesService();
  @Provide('funcionalidadesService') private funcionalidadesService = () => new FuncionalidadesService();
  @Provide('usuariosService') private usuariosService = () => new UsuariosService();
  @Provide('regimenFisService') private regimenFisService = () => new RegimenFisService();
  @Provide('estadoService') private estadoService = () => new EstadoService();
  @Provide('estatusCFDIService') private estatusCFDIService = () => new EstatusCFDIService();
  @Provide('motivosCancelService') private motivosCancelService = () => new MotivosCancelService();
  @Provide('tipoCFDIService') private tipoCFDIService = () => new TipoCFDIService();
  @Provide('tipoReciboService') private tipoReciboService = () => new TipoReciboService();
  @Provide('repGraficaService') private repGraficaService = () => new RepGraficaService();
  // jhipster-needle-add-entity-service-to-entities-component - JHipster will import entities services here
}
