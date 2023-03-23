import { Component, Vue, Inject } from 'vue-property-decorator';

import { IFTP } from '@/shared/model/ftp.model';
import FTPService from './ftp.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class FTPDetails extends Vue {
  @Inject('fTPService') private fTPService: () => FTPService;
  @Inject('alertService') private alertService: () => AlertService;

  public fTP: IFTP = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.fTPId) {
        vm.retrieveFTP(to.params.fTPId);
      }
    });
  }

  public retrieveFTP(fTPId) {
    this.fTPService()
      .find(fTPId)
      .then(res => {
        this.fTP = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
