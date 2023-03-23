import { Component, Vue, Inject } from 'vue-property-decorator';

import { IPerfiles } from '@/shared/model/perfiles.model';
import PerfilesService from './perfiles.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class PerfilesDetails extends Vue {
  @Inject('perfilesService') private perfilesService: () => PerfilesService;
  @Inject('alertService') private alertService: () => AlertService;

  public perfiles: IPerfiles = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.perfilesId) {
        vm.retrievePerfiles(to.params.perfilesId);
      }
    });
  }

  public retrievePerfiles(perfilesId) {
    this.perfilesService()
      .find(perfilesId)
      .then(res => {
        this.perfiles = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
