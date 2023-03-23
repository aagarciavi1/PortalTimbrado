import { Component, Vue, Inject } from 'vue-property-decorator';

import { IRepGrafica } from '@/shared/model/rep-grafica.model';
import RepGraficaService from './rep-grafica.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class RepGraficaDetails extends Vue {
  @Inject('repGraficaService') private repGraficaService: () => RepGraficaService;
  @Inject('alertService') private alertService: () => AlertService;

  public repGrafica: IRepGrafica = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.repGraficaId) {
        vm.retrieveRepGrafica(to.params.repGraficaId);
      }
    });
  }

  public retrieveRepGrafica(repGraficaId) {
    this.repGraficaService()
      .find(repGraficaId)
      .then(res => {
        this.repGrafica = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
