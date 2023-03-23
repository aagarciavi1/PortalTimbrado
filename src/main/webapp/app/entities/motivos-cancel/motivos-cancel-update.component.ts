import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import { IMotivosCancel, MotivosCancel } from '@/shared/model/motivos-cancel.model';
import MotivosCancelService from './motivos-cancel.service';
import { Estatus } from '@/shared/model/enumerations/estatus.model';

const validations: any = {
  motivosCancel: {
    fecha: {},
    motivoCancel: {},
    estatus: {},
    usuario: {},
    fechaMod: {},
  },
};

@Component({
  validations,
})
export default class MotivosCancelUpdate extends Vue {
  @Inject('motivosCancelService') private motivosCancelService: () => MotivosCancelService;
  @Inject('alertService') private alertService: () => AlertService;

  public motivosCancel: IMotivosCancel = new MotivosCancel();
  public estatusValues: string[] = Object.keys(Estatus);
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.motivosCancelId) {
        vm.retrieveMotivosCancel(to.params.motivosCancelId);
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
    if (this.motivosCancel.id) {
      this.motivosCancelService()
        .update(this.motivosCancel)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('proyecto03App.motivosCancel.updated', { param: param.id });
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
      this.motivosCancelService()
        .create(this.motivosCancel)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('proyecto03App.motivosCancel.created', { param: param.id });
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

  public retrieveMotivosCancel(motivosCancelId): void {
    this.motivosCancelService()
      .find(motivosCancelId)
      .then(res => {
        this.motivosCancel = res;
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
