import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore
const Entities = () => import('@/entities/entities.vue');

// prettier-ignore
const RegistInstit = () => import('@/entities/regist-instit/regist-instit.vue');
// prettier-ignore
const RegistInstitUpdate = () => import('@/entities/regist-instit/regist-instit-update.vue');
// prettier-ignore
const RegistInstitDetails = () => import('@/entities/regist-instit/regist-instit-details.vue');
// prettier-ignore
const Parametros = () => import('@/entities/parametros/parametros.vue');
// prettier-ignore
const ParametrosUpdate = () => import('@/entities/parametros/parametros-update.vue');
// prettier-ignore
const ParametrosDetails = () => import('@/entities/parametros/parametros-details.vue');
// prettier-ignore
const Recibo = () => import('@/entities/recibo/recibo.vue');
// prettier-ignore
const ReciboUpdate = () => import('@/entities/recibo/recibo-update.vue');
// prettier-ignore
const ReciboDetails = () => import('@/entities/recibo/recibo-details.vue');
// prettier-ignore
const FTP = () => import('@/entities/ftp/ftp.vue');
// prettier-ignore
const FTPUpdate = () => import('@/entities/ftp/ftp-update.vue');
// prettier-ignore
const FTPDetails = () => import('@/entities/ftp/ftp-details.vue');
// prettier-ignore
const Notificaciones = () => import('@/entities/notificaciones/notificaciones.vue');
// prettier-ignore
const NotificacionesUpdate = () => import('@/entities/notificaciones/notificaciones-update.vue');
// prettier-ignore
const NotificacionesDetails = () => import('@/entities/notificaciones/notificaciones-details.vue');
// prettier-ignore
const Perfiles = () => import('@/entities/perfiles/perfiles.vue');
// prettier-ignore
const PerfilesUpdate = () => import('@/entities/perfiles/perfiles-update.vue');
// prettier-ignore
const PerfilesDetails = () => import('@/entities/perfiles/perfiles-details.vue');
// prettier-ignore
const Funcionalidades = () => import('@/entities/funcionalidades/funcionalidades.vue');
// prettier-ignore
const FuncionalidadesUpdate = () => import('@/entities/funcionalidades/funcionalidades-update.vue');
// prettier-ignore
const FuncionalidadesDetails = () => import('@/entities/funcionalidades/funcionalidades-details.vue');
// prettier-ignore
const Usuarios = () => import('@/entities/usuarios/usuarios.vue');
// prettier-ignore
const UsuariosUpdate = () => import('@/entities/usuarios/usuarios-update.vue');
// prettier-ignore
const UsuariosDetails = () => import('@/entities/usuarios/usuarios-details.vue');
// prettier-ignore
const RegimenFis = () => import('@/entities/regimen-fis/regimen-fis.vue');
// prettier-ignore
const RegimenFisUpdate = () => import('@/entities/regimen-fis/regimen-fis-update.vue');
// prettier-ignore
const RegimenFisDetails = () => import('@/entities/regimen-fis/regimen-fis-details.vue');
// prettier-ignore
const Estado = () => import('@/entities/estado/estado.vue');
// prettier-ignore
const EstadoUpdate = () => import('@/entities/estado/estado-update.vue');
// prettier-ignore
const EstadoDetails = () => import('@/entities/estado/estado-details.vue');
// prettier-ignore
const EstatusCFDI = () => import('@/entities/estatus-cfdi/estatus-cfdi.vue');
// prettier-ignore
const EstatusCFDIUpdate = () => import('@/entities/estatus-cfdi/estatus-cfdi-update.vue');
// prettier-ignore
const EstatusCFDIDetails = () => import('@/entities/estatus-cfdi/estatus-cfdi-details.vue');
// prettier-ignore
const MotivosCancel = () => import('@/entities/motivos-cancel/motivos-cancel.vue');
// prettier-ignore
const MotivosCancelUpdate = () => import('@/entities/motivos-cancel/motivos-cancel-update.vue');
// prettier-ignore
const MotivosCancelDetails = () => import('@/entities/motivos-cancel/motivos-cancel-details.vue');
// prettier-ignore
const TipoCFDI = () => import('@/entities/tipo-cfdi/tipo-cfdi.vue');
// prettier-ignore
const TipoCFDIUpdate = () => import('@/entities/tipo-cfdi/tipo-cfdi-update.vue');
// prettier-ignore
const TipoCFDIDetails = () => import('@/entities/tipo-cfdi/tipo-cfdi-details.vue');
// prettier-ignore
const TipoRecibo = () => import('@/entities/tipo-recibo/tipo-recibo.vue');
// prettier-ignore
const TipoReciboUpdate = () => import('@/entities/tipo-recibo/tipo-recibo-update.vue');
// prettier-ignore
const TipoReciboDetails = () => import('@/entities/tipo-recibo/tipo-recibo-details.vue');
// prettier-ignore
const RepGrafica = () => import('@/entities/rep-grafica/rep-grafica.vue');
// prettier-ignore
const RepGraficaUpdate = () => import('@/entities/rep-grafica/rep-grafica-update.vue');
// prettier-ignore
const RepGraficaDetails = () => import('@/entities/rep-grafica/rep-grafica-details.vue');
// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default {
  path: '/',
  component: Entities,
  children: [
    {
      path: 'regist-instit',
      name: 'RegistInstit',
      component: RegistInstit,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'regist-instit/new',
      name: 'RegistInstitCreate',
      component: RegistInstitUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'regist-instit/:registInstitId/edit',
      name: 'RegistInstitEdit',
      component: RegistInstitUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'regist-instit/:registInstitId/view',
      name: 'RegistInstitView',
      component: RegistInstitDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'parametros',
      name: 'Parametros',
      component: Parametros,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'parametros/new',
      name: 'ParametrosCreate',
      component: ParametrosUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'parametros/:parametrosId/edit',
      name: 'ParametrosEdit',
      component: ParametrosUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'parametros/:parametrosId/view',
      name: 'ParametrosView',
      component: ParametrosDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'recibo',
      name: 'Recibo',
      component: Recibo,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'recibo/new',
      name: 'ReciboCreate',
      component: ReciboUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'recibo/:reciboId/edit',
      name: 'ReciboEdit',
      component: ReciboUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'recibo/:reciboId/view',
      name: 'ReciboView',
      component: ReciboDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'ftp',
      name: 'FTP',
      component: FTP,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'ftp/new',
      name: 'FTPCreate',
      component: FTPUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'ftp/:fTPId/edit',
      name: 'FTPEdit',
      component: FTPUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'ftp/:fTPId/view',
      name: 'FTPView',
      component: FTPDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'notificaciones',
      name: 'Notificaciones',
      component: Notificaciones,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'notificaciones/new',
      name: 'NotificacionesCreate',
      component: NotificacionesUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'notificaciones/:notificacionesId/edit',
      name: 'NotificacionesEdit',
      component: NotificacionesUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'notificaciones/:notificacionesId/view',
      name: 'NotificacionesView',
      component: NotificacionesDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'perfiles',
      name: 'Perfiles',
      component: Perfiles,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'perfiles/new',
      name: 'PerfilesCreate',
      component: PerfilesUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'perfiles/:perfilesId/edit',
      name: 'PerfilesEdit',
      component: PerfilesUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'perfiles/:perfilesId/view',
      name: 'PerfilesView',
      component: PerfilesDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'funcionalidades',
      name: 'Funcionalidades',
      component: Funcionalidades,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'funcionalidades/new',
      name: 'FuncionalidadesCreate',
      component: FuncionalidadesUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'funcionalidades/:funcionalidadesId/edit',
      name: 'FuncionalidadesEdit',
      component: FuncionalidadesUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'funcionalidades/:funcionalidadesId/view',
      name: 'FuncionalidadesView',
      component: FuncionalidadesDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'usuarios',
      name: 'Usuarios',
      component: Usuarios,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'usuarios/new',
      name: 'UsuariosCreate',
      component: UsuariosUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'usuarios/:usuariosId/edit',
      name: 'UsuariosEdit',
      component: UsuariosUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'usuarios/:usuariosId/view',
      name: 'UsuariosView',
      component: UsuariosDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'regimen-fis',
      name: 'RegimenFis',
      component: RegimenFis,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'regimen-fis/new',
      name: 'RegimenFisCreate',
      component: RegimenFisUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'regimen-fis/:regimenFisId/edit',
      name: 'RegimenFisEdit',
      component: RegimenFisUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'regimen-fis/:regimenFisId/view',
      name: 'RegimenFisView',
      component: RegimenFisDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'estado',
      name: 'Estado',
      component: Estado,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'estado/new',
      name: 'EstadoCreate',
      component: EstadoUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'estado/:estadoId/edit',
      name: 'EstadoEdit',
      component: EstadoUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'estado/:estadoId/view',
      name: 'EstadoView',
      component: EstadoDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'estatus-cfdi',
      name: 'EstatusCFDI',
      component: EstatusCFDI,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'estatus-cfdi/new',
      name: 'EstatusCFDICreate',
      component: EstatusCFDIUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'estatus-cfdi/:estatusCFDIId/edit',
      name: 'EstatusCFDIEdit',
      component: EstatusCFDIUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'estatus-cfdi/:estatusCFDIId/view',
      name: 'EstatusCFDIView',
      component: EstatusCFDIDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'motivos-cancel',
      name: 'MotivosCancel',
      component: MotivosCancel,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'motivos-cancel/new',
      name: 'MotivosCancelCreate',
      component: MotivosCancelUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'motivos-cancel/:motivosCancelId/edit',
      name: 'MotivosCancelEdit',
      component: MotivosCancelUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'motivos-cancel/:motivosCancelId/view',
      name: 'MotivosCancelView',
      component: MotivosCancelDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'tipo-cfdi',
      name: 'TipoCFDI',
      component: TipoCFDI,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'tipo-cfdi/new',
      name: 'TipoCFDICreate',
      component: TipoCFDIUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'tipo-cfdi/:tipoCFDIId/edit',
      name: 'TipoCFDIEdit',
      component: TipoCFDIUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'tipo-cfdi/:tipoCFDIId/view',
      name: 'TipoCFDIView',
      component: TipoCFDIDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'tipo-recibo',
      name: 'TipoRecibo',
      component: TipoRecibo,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'tipo-recibo/new',
      name: 'TipoReciboCreate',
      component: TipoReciboUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'tipo-recibo/:tipoReciboId/edit',
      name: 'TipoReciboEdit',
      component: TipoReciboUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'tipo-recibo/:tipoReciboId/view',
      name: 'TipoReciboView',
      component: TipoReciboDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'rep-grafica',
      name: 'RepGrafica',
      component: RepGrafica,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'rep-grafica/new',
      name: 'RepGraficaCreate',
      component: RepGraficaUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'rep-grafica/:repGraficaId/edit',
      name: 'RepGraficaEdit',
      component: RepGraficaUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'rep-grafica/:repGraficaId/view',
      name: 'RepGraficaView',
      component: RepGraficaDetails,
      meta: { authorities: [Authority.USER] },
    },
    // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
  ],
};
