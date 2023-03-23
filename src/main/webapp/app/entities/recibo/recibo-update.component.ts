import { Component, Vue, Inject } from 'vue-property-decorator';

import { required, numeric } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';

import TipoReciboService from '@/entities/tipo-recibo/tipo-recibo.service';
import { ITipoRecibo } from '@/shared/model/tipo-recibo.model';

import RepGraficaService from '@/entities/rep-grafica/rep-grafica.service';
import { IRepGrafica } from '@/shared/model/rep-grafica.model';

import ParametrosService from '@/entities/parametros/parametros.service';
import { IParametros } from '@/shared/model/parametros.model';

import { IRecibo, Recibo } from '@/shared/model/recibo.model';
import ReciboService from './recibo.service';

const validations: any = {
  recibo: {
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
    tipoCFDI: {
      required,
    },
    clave: {},
    envioConCFDI: {},
    envioSinCFDI: {},
    aplicaLeyenda: {},
    leyenda: {},
    usuario: {},
    fechaMod: {},
  },
};

@Component({
  validations,
})
export default class ReciboUpdate extends Vue {
  @Inject('reciboService') private reciboService: () => ReciboService;
  @Inject('alertService') private alertService: () => AlertService;

  public recibo: IRecibo = new Recibo();

  @Inject('tipoReciboService') private tipoReciboService: () => TipoReciboService;

  public tipoRecibos: ITipoRecibo[] = [];

  @Inject('repGraficaService') private repGraficaService: () => RepGraficaService;

  public repGraficas: IRepGrafica[] = [];

  @Inject('parametrosService') private parametrosService: () => ParametrosService;

  public parametros: IParametros[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.reciboId) {
        vm.retrieveRecibo(to.params.reciboId);
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
    if (this.recibo.id) {
      this.reciboService()
        .update(this.recibo)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('proyecto03App.recibo.updated', { param: param.id });
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
      this.reciboService()
        .create(this.recibo)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('proyecto03App.recibo.created', { param: param.id });
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

  public retrieveRecibo(reciboId): void {
    this.reciboService()
      .find(reciboId)
      .then(res => {
        this.recibo = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.tipoReciboService()
      .retrieve()
      .then(res => {
        this.tipoRecibos = res.data;
      });
    this.repGraficaService()
      .retrieve()
      .then(res => {
        this.repGraficas = res.data;
      });
    this.parametrosService()
      .retrieve()
      .then(res => {
        this.parametros = res.data;
      });
  }
}
