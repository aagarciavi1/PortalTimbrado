import { Component, Vue, Inject } from 'vue-property-decorator';

import { ITipoRecibo } from '@/shared/model/tipo-recibo.model';
import TipoReciboService from './tipo-recibo.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class TipoReciboDetails extends Vue {
  @Inject('tipoReciboService') private tipoReciboService: () => TipoReciboService;
  @Inject('alertService') private alertService: () => AlertService;

  public tipoRecibo: ITipoRecibo = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.tipoReciboId) {
        vm.retrieveTipoRecibo(to.params.tipoReciboId);
      }
    });
  }

  public retrieveTipoRecibo(tipoReciboId) {
    this.tipoReciboService()
      .find(tipoReciboId)
      .then(res => {
        this.tipoRecibo = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
