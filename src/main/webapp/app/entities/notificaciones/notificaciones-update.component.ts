import { Component, Vue, Inject } from 'vue-property-decorator';

import { required, numeric } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';

import RegistInstitService from '@/entities/regist-instit/regist-instit.service';
import { IRegistInstit } from '@/shared/model/regist-instit.model';

import TipoCFDIService from '@/entities/tipo-cfdi/tipo-cfdi.service';
import { ITipoCFDI } from '@/shared/model/tipo-cfdi.model';

import TipoReciboService from '@/entities/tipo-recibo/tipo-recibo.service';
import { ITipoRecibo } from '@/shared/model/tipo-recibo.model';

import { INotificaciones, Notificaciones } from '@/shared/model/notificaciones.model';
import NotificacionesService from './notificaciones.service';
import { TipoNot } from '@/shared/model/enumerations/tipo-not.model';

const validations: any = {
  notificaciones: {
    fecha: {
      required,
    },
    idInstit: {
      required,
      numeric,
    },
    nivel: {
      required,
      numeric,
    },
    tipoNot: {},
    clave: {},
    asunto: {},
    texto: {},
    piePagina: {},
    usuario: {},
    fechaMod: {},
  },
};

@Component({
  validations,
})
export default class NotificacionesUpdate extends Vue {
  @Inject('notificacionesService') private notificacionesService: () => NotificacionesService;
  @Inject('alertService') private alertService: () => AlertService;

  public notificaciones: INotificaciones = new Notificaciones();

  @Inject('registInstitService') private registInstitService: () => RegistInstitService;

  public registInstits: IRegistInstit[] = [];

  @Inject('tipoCFDIService') private tipoCFDIService: () => TipoCFDIService;

  public tipoCFDIS: ITipoCFDI[] = [];

  @Inject('tipoReciboService') private tipoReciboService: () => TipoReciboService;

  public tipoRecibos: ITipoRecibo[] = [];
  public tipoNotValues: string[] = Object.keys(TipoNot);
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.notificacionesId) {
        vm.retrieveNotificaciones(to.params.notificacionesId);
      }
      vm.initRelationships();
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.notificaciones.id) {
      this.notificacionesService()
        .update(this.notificaciones)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('proyecto03App.notificaciones.updated', { param: param.id });
          return (this.$root as any).$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    } else {
      this.notificacionesService()
        .create(this.notificaciones)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('proyecto03App.notificaciones.created', { param: param.id });
          (this.$root as any).$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    }
  }

  public retrieveNotificaciones(notificacionesId): void {
    this.notificacionesService()
      .find(notificacionesId)
      .then(res => {
        this.notificaciones = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.registInstitService()
      .retrieve()
      .then(res => {
        this.registInstits = res.data;
      });
    this.tipoCFDIService()
      .retrieve()
      .then(res => {
        this.tipoCFDIS = res.data;
      });
    this.tipoReciboService()
      .retrieve()
      .then(res => {
        this.tipoRecibos = res.data;
      });
  }
}
