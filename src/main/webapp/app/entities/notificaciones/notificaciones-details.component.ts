import { Component, Vue, Inject } from 'vue-property-decorator';

import { INotificaciones } from '@/shared/model/notificaciones.model';
import NotificacionesService from './notificaciones.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class NotificacionesDetails extends Vue {
  @Inject('notificacionesService') private notificacionesService: () => NotificacionesService;
  @Inject('alertService') private alertService: () => AlertService;

  public notificaciones: INotificaciones = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.notificacionesId) {
        vm.retrieveNotificaciones(to.params.notificacionesId);
      }
    });
  }

  public retrieveNotificaciones(notificacionesId) {
    this.notificacionesService()
      .find(notificacionesId)
      .then(res => {
        this.notificaciones = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
