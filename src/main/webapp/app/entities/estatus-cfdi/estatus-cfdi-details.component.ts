import { Component, Vue, Inject } from 'vue-property-decorator';

import { IEstatusCFDI } from '@/shared/model/estatus-cfdi.model';
import EstatusCFDIService from './estatus-cfdi.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class EstatusCFDIDetails extends Vue {
  @Inject('estatusCFDIService') private estatusCFDIService: () => EstatusCFDIService;
  @Inject('alertService') private alertService: () => AlertService;

  public estatusCFDI: IEstatusCFDI = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.estatusCFDIId) {
        vm.retrieveEstatusCFDI(to.params.estatusCFDIId);
      }
    });
  }

  public retrieveEstatusCFDI(estatusCFDIId) {
    this.estatusCFDIService()
      .find(estatusCFDIId)
      .then(res => {
        this.estatusCFDI = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
