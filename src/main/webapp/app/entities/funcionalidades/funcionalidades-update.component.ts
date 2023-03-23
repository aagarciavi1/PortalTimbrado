import { Component, Vue, Inject } from 'vue-property-decorator';

import { required } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';

import PerfilesService from '@/entities/perfiles/perfiles.service';
import { IPerfiles } from '@/shared/model/perfiles.model';

import { IFuncionalidades, Funcionalidades } from '@/shared/model/funcionalidades.model';
import FuncionalidadesService from './funcionalidades.service';
import { Funcionalidad } from '@/shared/model/enumerations/funcionalidad.model';

const validations: any = {
  funcionalidades: {
    fecha: {
      required,
    },
    perfil: {
      required,
    },
    funcionalidad: {},
    alta: {},
    editar: {},
    activarInact: {},
    consulta: {},
    usuario: {},
    fechaMod: {},
  },
};

@Component({
  validations,
})
export default class FuncionalidadesUpdate extends Vue {
  @Inject('funcionalidadesService') private funcionalidadesService: () => FuncionalidadesService;
  @Inject('alertService') private alertService: () => AlertService;

  public funcionalidades: IFuncionalidades = new Funcionalidades();

  @Inject('perfilesService') private perfilesService: () => PerfilesService;

  public perfiles: IPerfiles[] = [];
  public funcionalidadValues: string[] = Object.keys(Funcionalidad);
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.funcionalidadesId) {
        vm.retrieveFuncionalidades(to.params.funcionalidadesId);
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
    if (this.funcionalidades.id) {
      this.funcionalidadesService()
        .update(this.funcionalidades)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('proyecto03App.funcionalidades.updated', { param: param.id });
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
      this.funcionalidadesService()
        .create(this.funcionalidades)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('proyecto03App.funcionalidades.created', { param: param.id });
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

  public retrieveFuncionalidades(funcionalidadesId): void {
    this.funcionalidadesService()
      .find(funcionalidadesId)
      .then(res => {
        this.funcionalidades = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.perfilesService()
      .retrieve()
      .then(res => {
        this.perfiles = res.data;
      });
  }
}
