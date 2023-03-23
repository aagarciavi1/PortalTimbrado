import { Component, Vue, Inject } from 'vue-property-decorator';

import { IMotivosCancel } from '@/shared/model/motivos-cancel.model';
import MotivosCancelService from './motivos-cancel.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class MotivosCancelDetails extends Vue {
  @Inject('motivosCancelService') private motivosCancelService: () => MotivosCancelService;
  @Inject('alertService') private alertService: () => AlertService;

  public motivosCancel: IMotivosCancel = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.motivosCancelId) {
        vm.retrieveMotivosCancel(to.params.motivosCancelId);
      }
    });
  }

  public retrieveMotivosCancel(motivosCancelId) {
    this.motivosCancelService()
      .find(motivosCancelId)
      .then(res => {
        this.motivosCancel = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
