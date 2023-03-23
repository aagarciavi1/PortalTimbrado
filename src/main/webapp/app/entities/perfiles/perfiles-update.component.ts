import { Component, Vue, Inject } from 'vue-property-decorator';

import { required } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';

import FuncionalidadesService from '@/entities/funcionalidades/funcionalidades.service';
import { IFuncionalidades } from '@/shared/model/funcionalidades.model';

import { IPerfiles, Perfiles } from '@/shared/model/perfiles.model';
import PerfilesService from './perfiles.service';

const validations: any = {
  perfiles: {
    fecha: {
      required,
    },
    perfil: {
      required,
    },
    usuario: {},
    fechaMod: {},
  },
};

@Component({
  validations,
})
export default class PerfilesUpdate extends Vue {
  @Inject('perfilesService') private perfilesService: () => PerfilesService;
  @Inject('alertService') private alertService: () => AlertService;

  public perfiles: IPerfiles = new Perfiles();

  @Inject('funcionalidadesService') private funcionalidadesService: () => FuncionalidadesService;

  public funcionalidades: IFuncionalidades[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.perfilesId) {
        vm.retrievePerfiles(to.params.perfilesId);
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
    if (this.perfiles.id) {
      this.perfilesService()
        .update(this.perfiles)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('proyecto03App.perfiles.updated', { param: param.id });
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
      this.perfilesService()
        .create(this.perfiles)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('proyecto03App.perfiles.created', { param: param.id });
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

  public retrievePerfiles(perfilesId): void {
    this.perfilesService()
      .find(perfilesId)
      .then(res => {
        this.perfiles = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.funcionalidadesService()
      .retrieve()
      .then(res => {
        this.funcionalidades = res.data;
      });
  }
}
