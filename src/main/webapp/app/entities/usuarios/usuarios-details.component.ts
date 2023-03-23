import { Component, Vue, Inject } from 'vue-property-decorator';

import { IUsuarios } from '@/shared/model/usuarios.model';
import UsuariosService from './usuarios.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class UsuariosDetails extends Vue {
  @Inject('usuariosService') private usuariosService: () => UsuariosService;
  @Inject('alertService') private alertService: () => AlertService;

  public usuarios: IUsuarios = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.usuariosId) {
        vm.retrieveUsuarios(to.params.usuariosId);
      }
    });
  }

  public retrieveUsuarios(usuariosId) {
    this.usuariosService()
      .find(usuariosId)
      .then(res => {
        this.usuarios = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
