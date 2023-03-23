import { Component, Vue, Inject } from 'vue-property-decorator';

import { IRecibo } from '@/shared/model/recibo.model';
import ReciboService from './recibo.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class ReciboDetails extends Vue {
  @Inject('reciboService') private reciboService: () => ReciboService;
  @Inject('alertService') private alertService: () => AlertService;

  public recibo: IRecibo = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.reciboId) {
        vm.retrieveRecibo(to.params.reciboId);
      }
    });
  }

  public retrieveRecibo(reciboId) {
    this.reciboService()
      .find(reciboId)
      .then(res => {
        this.recibo = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
