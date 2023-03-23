import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import { ITipoRecibo, TipoRecibo } from '@/shared/model/tipo-recibo.model';
import TipoReciboService from './tipo-recibo.service';
import { Estatus } from '@/shared/model/enumerations/estatus.model';

const validations: any = {
  tipoRecibo: {
    fecha: {},
    tipoRecibo: {},
    clave: {},
    estatus: {},
    usuario: {},
    fechaMod: {},
  },
};

@Component({
  validations,
})
export default class TipoReciboUpdate extends Vue {
  @Inject('tipoReciboService') private tipoReciboService: () => TipoReciboService;
  @Inject('alertService') private alertService: () => AlertService;

  public tipoRecibo: ITipoRecibo = new TipoRecibo();
  public estatusValues: string[] = Object.keys(Estatus);
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.tipoReciboId) {
        vm.retrieveTipoRecibo(to.params.tipoReciboId);
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
    if (this.tipoRecibo.id) {
      this.tipoReciboService()
        .update(this.tipoRecibo)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('proyecto03App.tipoRecibo.updated', { param: param.id });
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
      this.tipoReciboService()
        .create(this.tipoRecibo)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('proyecto03App.tipoRecibo.created', { param: param.id });
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

  public retrieveTipoRecibo(tipoReciboId): void {
    this.tipoReciboService()
      .find(tipoReciboId)
      .then(res => {
        this.tipoRecibo = res;
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
