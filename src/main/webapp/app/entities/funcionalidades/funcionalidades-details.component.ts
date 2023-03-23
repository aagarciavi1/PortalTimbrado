import { Component, Vue, Inject } from 'vue-property-decorator';

import { IFuncionalidades } from '@/shared/model/funcionalidades.model';
import FuncionalidadesService from './funcionalidades.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class FuncionalidadesDetails extends Vue {
  @Inject('funcionalidadesService') private funcionalidadesService: () => FuncionalidadesService;
  @Inject('alertService') private alertService: () => AlertService;

  public funcionalidades: IFuncionalidades = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.funcionalidadesId) {
        vm.retrieveFuncionalidades(to.params.funcionalidadesId);
      }
    });
  }

  public retrieveFuncionalidades(funcionalidadesId) {
    this.funcionalidadesService()
      .find(funcionalidadesId)
      .then(res => {
        this.funcionalidades = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
