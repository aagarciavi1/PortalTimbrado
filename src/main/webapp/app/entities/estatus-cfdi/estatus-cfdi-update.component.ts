import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import { IEstatusCFDI, EstatusCFDI } from '@/shared/model/estatus-cfdi.model';
import EstatusCFDIService from './estatus-cfdi.service';
import { Estatus } from '@/shared/model/enumerations/estatus.model';

const validations: any = {
  estatusCFDI: {
    fecha: {},
    descEstCFDI: {},
    estatus: {},
    usuario: {},
    fechaMod: {},
  },
};

@Component({
  validations,
})
export default class EstatusCFDIUpdate extends Vue {
  @Inject('estatusCFDIService') private estatusCFDIService: () => EstatusCFDIService;
  @Inject('alertService') private alertService: () => AlertService;

  public estatusCFDI: IEstatusCFDI = new EstatusCFDI();
  public estatusValues: string[] = Object.keys(Estatus);
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.estatusCFDIId) {
        vm.retrieveEstatusCFDI(to.params.estatusCFDIId);
      }
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
    if (this.estatusCFDI.id) {
      this.estatusCFDIService()
        .update(this.estatusCFDI)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('proyecto03App.estatusCFDI.updated', { param: param.id });
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
      this.estatusCFDIService()
        .create(this.estatusCFDI)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('proyecto03App.estatusCFDI.created', { param: param.id });
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

  public retrieveEstatusCFDI(estatusCFDIId): void {
    this.estatusCFDIService()
      .find(estatusCFDIId)
      .then(res => {
        this.estatusCFDI = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
