import { Component, Vue, Inject } from 'vue-property-decorator';

import { ITipoCFDI } from '@/shared/model/tipo-cfdi.model';
import TipoCFDIService from './tipo-cfdi.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class TipoCFDIDetails extends Vue {
  @Inject('tipoCFDIService') private tipoCFDIService: () => TipoCFDIService;
  @Inject('alertService') private alertService: () => AlertService;

  public tipoCFDI: ITipoCFDI = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.tipoCFDIId) {
        vm.retrieveTipoCFDI(to.params.tipoCFDIId);
      }
    });
  }

  public retrieveTipoCFDI(tipoCFDIId) {
    this.tipoCFDIService()
      .find(tipoCFDIId)
      .then(res => {
        this.tipoCFDI = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
