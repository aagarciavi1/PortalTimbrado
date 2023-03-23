import { Component, Vue, Inject } from 'vue-property-decorator';

import { required, numeric } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';

import RegistInstitService from '@/entities/regist-instit/regist-instit.service';
import { IRegistInstit } from '@/shared/model/regist-instit.model';

import TipoCFDIService from '@/entities/tipo-cfdi/tipo-cfdi.service';
import { ITipoCFDI } from '@/shared/model/tipo-cfdi.model';

import TipoReciboService from '@/entities/tipo-recibo/tipo-recibo.service';
import { ITipoRecibo } from '@/shared/model/tipo-recibo.model';

import { IFTP, FTP } from '@/shared/model/ftp.model';
import FTPService from './ftp.service';

const validations: any = {
  fTP: {
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
    clave: {},
    carpetaFTP: {},
    subcarpetaFTP: {},
    descripcionFTP: {},
    ipFTP: {},
    puerto: {},
    usuarioFTP: {},
    contrasena: {},
    usuario: {},
    fechaMod: {},
  },
};

@Component({
  validations,
})
export default class FTPUpdate extends Vue {
  @Inject('fTPService') private fTPService: () => FTPService;
  @Inject('alertService') private alertService: () => AlertService;

  public fTP: IFTP = new FTP();

  @Inject('registInstitService') private registInstitService: () => RegistInstitService;

  public registInstits: IRegistInstit[] = [];

  @Inject('tipoCFDIService') private tipoCFDIService: () => TipoCFDIService;

  public tipoCFDIS: ITipoCFDI[] = [];

  @Inject('tipoReciboService') private tipoReciboService: () => TipoReciboService;

  public tipoRecibos: ITipoRecibo[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.fTPId) {
        vm.retrieveFTP(to.params.fTPId);
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
    if (this.fTP.id) {
      this.fTPService()
        .update(this.fTP)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('proyecto03App.fTP.updated', { param: param.id });
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
      this.fTPService()
        .create(this.fTP)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('proyecto03App.fTP.created', { param: param.id });
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

  public retrieveFTP(fTPId): void {
    this.fTPService()
      .find(fTPId)
      .then(res => {
        this.fTP = res;
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
