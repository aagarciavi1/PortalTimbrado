import { Component, Vue, Inject } from 'vue-property-decorator';

import { IRegimenFis } from '@/shared/model/regimen-fis.model';
import RegimenFisService from './regimen-fis.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class RegimenFisDetails extends Vue {
  @Inject('regimenFisService') private regimenFisService: () => RegimenFisService;
  @Inject('alertService') private alertService: () => AlertService;

  public regimenFis: IRegimenFis = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.regimenFisId) {
        vm.retrieveRegimenFis(to.params.regimenFisId);
      }
    });
  }

  public retrieveRegimenFis(regimenFisId) {
    this.regimenFisService()
      .find(regimenFisId)
      .then(res => {
        this.regimenFis = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
