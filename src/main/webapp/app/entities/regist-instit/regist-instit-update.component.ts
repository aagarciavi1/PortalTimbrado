import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { required, numeric } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';

import RegimenFisService from '@/entities/regimen-fis/regimen-fis.service';
import { IRegimenFis } from '@/shared/model/regimen-fis.model';

import EstadoService from '@/entities/estado/estado.service';
import { IEstado } from '@/shared/model/estado.model';

import { IRegistInstit, RegistInstit } from '@/shared/model/regist-instit.model';
import RegistInstitService from './regist-instit.service';
import { Estatus } from '@/shared/model/enumerations/estatus.model';

const validations: any = {
  registInstit: {
    fecha: {
      required,
    },
    idInstit: {
      required,
      numeric,
    },
    estatus: {},
    nivel: {
      required,
      numeric,
    },
    razonSocial: {
      required,
    },
    rFC: {
      required,
    },
    pais: {},
    municipio: {},
    localidad: {},
    codigoPostal: {},
    colonia: {},
    calle: {},
    numExt: {},
    numInt: {},
    logo: {},
    certificado: {},
    llavePriv: {},
    contrasena: {},
    fechaExp: {},
    dias: {},
    correo: {},
    usuario: {},
    fechaMod: {},
  },
};

@Component({
  validations,
})
export default class RegistInstitUpdate extends mixins(JhiDataUtils) {
  @Inject('registInstitService') private registInstitService: () => RegistInstitService;
  @Inject('alertService') private alertService: () => AlertService;

  public registInstit: IRegistInstit = new RegistInstit();

  @Inject('regimenFisService') private regimenFisService: () => RegimenFisService;

  public regimenFis: IRegimenFis[] = [];

  @Inject('estadoService') private estadoService: () => EstadoService;

  public estados: IEstado[] = [];

  public registInstits: IRegistInstit[] = [];
  public estatusValues: string[] = Object.keys(Estatus);
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.registInstitId) {
        vm.retrieveRegistInstit(to.params.registInstitId);
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
    if (this.registInstit.id) {
      this.registInstitService()
        .update(this.registInstit)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('proyecto03App.registInstit.updated', { param: param.id });
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
      this.registInstitService()
        .create(this.registInstit)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('proyecto03App.registInstit.created', { param: param.id });
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

  public retrieveRegistInstit(registInstitId): void {
    this.registInstitService()
      .find(registInstitId)
      .then(res => {
        this.registInstit = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.regimenFisService()
      .retrieve()
      .then(res => {
        this.regimenFis = res.data;
      });
    this.estadoService()
      .retrieve()
      .then(res => {
        this.estados = res.data;
      });
    this.registInstitService()
      .retrieve()
      .then(res => {
        this.registInstits = res.data;
      });
  }
}
