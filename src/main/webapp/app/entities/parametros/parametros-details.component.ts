import { Component, Vue, Inject } from 'vue-property-decorator';

import { IParametros } from '@/shared/model/parametros.model';
import ParametrosService from './parametros.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class ParametrosDetails extends Vue {
  @Inject('parametrosService') private parametrosService: () => ParametrosService;
  @Inject('alertService') private alertService: () => AlertService;

  public parametros: IParametros = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.parametrosId) {
        vm.retrieveParametros(to.params.parametrosId);
      }
    });
  }

  public retrieveParametros(parametrosId) {
    this.parametrosService()
      .find(parametrosId)
      .then(res => {
        this.parametros = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
