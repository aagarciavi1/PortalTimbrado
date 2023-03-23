import { Component, Vue, Inject } from 'vue-property-decorator';

import { required, numeric } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';

import RegistInstitService from '@/entities/regist-instit/regist-instit.service';
import { IRegistInstit } from '@/shared/model/regist-instit.model';

import PerfilesService from '@/entities/perfiles/perfiles.service';
import { IPerfiles } from '@/shared/model/perfiles.model';

import TipoCFDIService from '@/entities/tipo-cfdi/tipo-cfdi.service';
import { ITipoCFDI } from '@/shared/model/tipo-cfdi.model';

import TipoReciboService from '@/entities/tipo-recibo/tipo-recibo.service';
import { ITipoRecibo } from '@/shared/model/tipo-recibo.model';

import { IUsuarios, Usuarios } from '@/shared/model/usuarios.model';
import UsuariosService from './usuarios.service';
import { Estatus } from '@/shared/model/enumerations/estatus.model';

const validations: any = {
  usuarios: {
    fecha: {
      required,
    },
    usuario: {
      required,
    },
    descr: {
      required,
    },
    idInstit: {
      required,
      numeric,
    },
    correo: {},
    contrasena: {},
    confirmarContras: {},
    estatus: {},
    usuarioCrea: {},
  },
};

@Component({
  validations,
})
export default class UsuariosUpdate extends Vue {
  @Inject('usuariosService') private usuariosService: () => UsuariosService;
  @Inject('alertService') private alertService: () => AlertService;

  public usuarios: IUsuarios = new Usuarios();

  @Inject('registInstitService') private registInstitService: () => RegistInstitService;

  public registInstits: IRegistInstit[] = [];

  @Inject('perfilesService') private perfilesService: () => PerfilesService;

  public perfiles: IPerfiles[] = [];

  @Inject('tipoCFDIService') private tipoCFDIService: () => TipoCFDIService;

  public tipoCFDIS: ITipoCFDI[] = [];

  @Inject('tipoReciboService') private tipoReciboService: () => TipoReciboService;

  public tipoRecibos: ITipoRecibo[] = [];
  public estatusValues: string[] = Object.keys(Estatus);
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.usuariosId) {
        vm.retrieveUsuarios(to.params.usuariosId);
      }
      vm.initRelationships();
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.usuarios.id) {
      this.usuariosService()
        .update(this.usuarios)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('proyecto03App.usuarios.updated', { param: param.id });
          return (this.$root as any).$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    } else {
      this.usuariosService()
        .create(this.usuarios)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('proyecto03App.usuarios.created', { param: param.id });
          (this.$root as any).$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    }
  }

  public retrieveUsuarios(usuariosId): void {
    this.usuariosService()
      .find(usuariosId)
      .then(res => {
        this.usuarios = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.registInstitService()
      .retrieve()
      .then(res => {
        this.registInstits = res.data;
      });
    this.perfilesService()
      .retrieve()
      .then(res => {
        this.perfiles = res.data;
      });
    this.tipoCFDIService()
      .retrieve()
      .then(res => {
        this.tipoCFDIS = res.data;
      });
    this.tipoReciboService()
      .retrieve()
      .then(res => {
        this.tipoRecibos = res.data;
      });
  }
}
