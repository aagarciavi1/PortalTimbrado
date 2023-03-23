import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import { ITipoCFDI, TipoCFDI } from '@/shared/model/tipo-cfdi.model';
import TipoCFDIService from './tipo-cfdi.service';
import { Estatus } from '@/shared/model/enumerations/estatus.model';

const validations: any = {
  tipoCFDI: {
    fecha: {},
    tipoCFDI: {},
    estatus: {},
    usuario: {},
    fechaMod: {},
  },
};

@Component({
  validations,
})
export default class TipoCFDIUpdate extends Vue {
  @Inject('tipoCFDIService') private tipoCFDIService: () => TipoCFDIService;
  @Inject('alertService') private alertService: () => AlertService;

  public tipoCFDI: ITipoCFDI = new TipoCFDI();
  public estatusValues: string[] = Object.keys(Estatus);
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.tipoCFDIId) {
        vm.retrieveTipoCFDI(to.params.tipoCFDIId);
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
    if (this.tipoCFDI.id) {
      this.tipoCFDIService()
        .update(this.tipoCFDI)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('proyecto03App.tipoCFDI.updated', { param: param.id });
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
      this.tipoCFDIService()
        .create(this.tipoCFDI)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('proyecto03App.tipoCFDI.created', { param: param.id });
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

  public retrieveTipoCFDI(tipoCFDIId): void {
    this.tipoCFDIService()
      .find(tipoCFDIId)
      .then(res => {
        this.tipoCFDI = res;
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
