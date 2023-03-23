import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import { IRepGrafica, RepGrafica } from '@/shared/model/rep-grafica.model';
import RepGraficaService from './rep-grafica.service';
import { Estatus } from '@/shared/model/enumerations/estatus.model';

const validations: any = {
  repGrafica: {
    fecha: {},
    repGrafica: {},
    estatus: {},
    usuario: {},
    fechaMod: {},
  },
};

@Component({
  validations,
})
export default class RepGraficaUpdate extends Vue {
  @Inject('repGraficaService') private repGraficaService: () => RepGraficaService;
  @Inject('alertService') private alertService: () => AlertService;

  public repGrafica: IRepGrafica = new RepGrafica();
  public estatusValues: string[] = Object.keys(Estatus);
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.repGraficaId) {
        vm.retrieveRepGrafica(to.params.repGraficaId);
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
    if (this.repGrafica.id) {
      this.repGraficaService()
        .update(this.repGrafica)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('proyecto03App.repGrafica.updated', { param: param.id });
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
      this.repGraficaService()
        .create(this.repGrafica)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('proyecto03App.repGrafica.created', { param: param.id });
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

  public retrieveRepGrafica(repGraficaId): void {
    this.repGraficaService()
      .find(repGraficaId)
      .then(res => {
        this.repGrafica = res;
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
