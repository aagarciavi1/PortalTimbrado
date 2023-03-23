import { Component, Vue, Inject } from 'vue-property-decorator';

import { required, numeric } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';

import RegistInstitService from '@/entities/regist-instit/regist-instit.service';
import { IRegistInstit } from '@/shared/model/regist-instit.model';

import TipoCFDIService from '@/entities/tipo-cfdi/tipo-cfdi.service';
import { ITipoCFDI } from '@/shared/model/tipo-cfdi.model';

import ReciboService from '@/entities/recibo/recibo.service';
import { IRecibo } from '@/shared/model/recibo.model';

import { IParametros, Parametros } from '@/shared/model/parametros.model';
import ParametrosService from './parametros.service';

const validations: any = {
  parametros: {
    fecha: {
      required,
    },
    idInstit: {
      required,
      numeric,
    },
    nivel: {
      required,
      numeric,
    },
    usuario: {},
    fechaMod: {},
  },
};

@Component({
  validations,
})
export default class ParametrosUpdate extends Vue {
  @Inject('parametrosService') private parametrosService: () => ParametrosService;
  @Inject('alertService') private alertService: () => AlertService;

  public parametros: IParametros = new Parametros();

  @Inject('registInstitService') private registInstitService: () => RegistInstitService;

  public registInstits: IRegistInstit[] = [];

  @Inject('tipoCFDIService') private tipoCFDIService: () => TipoCFDIService;

  public tipoCFDIS: ITipoCFDI[] = [];

  @Inject('reciboService') private reciboService: () => ReciboService;

  public recibos: IRecibo[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.parametrosId) {
        vm.retrieveParametros(to.params.parametrosId);
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
    if (this.parametros.id) {
      this.parametrosService()
        .update(this.parametros)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('proyecto03App.parametros.updated', { param: param.id });
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
      this.parametrosService()
        .create(this.parametros)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('proyecto03App.parametros.created', { param: param.id });
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

  public retrieveParametros(parametrosId): void {
    this.parametrosService()
      .find(parametrosId)
      .then(res => {
        this.parametros = res;
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
    this.tipoCFDIService()
      .retrieve()
      .then(res => {
        this.tipoCFDIS = res.data;
      });
    this.reciboService()
      .retrieve()
      .then(res => {
        this.recibos = res.data;
      });
  }
}
