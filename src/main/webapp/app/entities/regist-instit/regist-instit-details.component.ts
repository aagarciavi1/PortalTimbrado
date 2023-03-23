import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { IRegistInstit } from '@/shared/model/regist-instit.model';
import RegistInstitService from './regist-instit.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class RegistInstitDetails extends mixins(JhiDataUtils) {
  @Inject('registInstitService') private registInstitService: () => RegistInstitService;
  @Inject('alertService') private alertService: () => AlertService;

  public registInstit: IRegistInstit = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.registInstitId) {
        vm.retrieveRegistInstit(to.params.registInstitId);
      }
    });
  }

  public retrieveRegistInstit(registInstitId) {
    this.registInstitService()
      .find(registInstitId)
      .then(res => {
        this.registInstit = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
