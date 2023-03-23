import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import { IRegimenFis, RegimenFis } from '@/shared/model/regimen-fis.model';
import RegimenFisService from './regimen-fis.service';
import { Estatus } from '@/shared/model/enumerations/estatus.model';

const validations: any = {
  regimenFis: {
    fecha: {},
    regimenF: {},
    estatus: {},
    usuario: {},
    fechaMod: {},
  },
};

@Component({
  validations,
})
export default class RegimenFisUpdate extends Vue {
  @Inject('regimenFisService') private regimenFisService: () => RegimenFisService;
  @Inject('alertService') private alertService: () => AlertService;

  public regimenFis: IRegimenFis = new RegimenFis();
  public estatusValues: string[] = Object.keys(Estatus);
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.regimenFisId) {
        vm.retrieveRegimenFis(to.params.regimenFisId);
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
    if (this.regimenFis.id) {
      this.regimenFisService()
        .update(this.regimenFis)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('proyecto03App.regimenFis.updated', { param: param.id });
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
      this.regimenFisService()
        .create(this.regimenFis)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('proyecto03App.regimenFis.created', { param: param.id });
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

  public retrieveRegimenFis(regimenFisId): void {
    this.regimenFisService()
      .find(regimenFisId)
      .then(res => {
        this.regimenFis = res;
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
