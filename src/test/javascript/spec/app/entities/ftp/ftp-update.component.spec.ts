/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import FTPUpdateComponent from '@/entities/ftp/ftp-update.vue';
import FTPClass from '@/entities/ftp/ftp-update.component';
import FTPService from '@/entities/ftp/ftp.service';

import RegistInstitService from '@/entities/regist-instit/regist-instit.service';

import TipoCFDIService from '@/entities/tipo-cfdi/tipo-cfdi.service';

import TipoReciboService from '@/entities/tipo-recibo/tipo-recibo.service';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.use(ToastPlugin);
localVue.component('font-awesome-icon', {});
localVue.component('b-input-group', {});
localVue.component('b-input-group-prepend', {});
localVue.component('b-form-datepicker', {});
localVue.component('b-form-input', {});

describe('Component Tests', () => {
  describe('FTP Management Update Component', () => {
    let wrapper: Wrapper<FTPClass>;
    let comp: FTPClass;
    let fTPServiceStub: SinonStubbedInstance<FTPService>;

    beforeEach(() => {
      fTPServiceStub = sinon.createStubInstance<FTPService>(FTPService);

      wrapper = shallowMount<FTPClass>(FTPUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          fTPService: () => fTPServiceStub,
          alertService: () => new AlertService(),

          registInstitService: () =>
            sinon.createStubInstance<RegistInstitService>(RegistInstitService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          tipoCFDIService: () =>
            sinon.createStubInstance<TipoCFDIService>(TipoCFDIService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          tipoReciboService: () =>
            sinon.createStubInstance<TipoReciboService>(TipoReciboService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.fTP = entity;
        fTPServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(fTPServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.fTP = entity;
        fTPServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(fTPServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundFTP = { id: 123 };
        fTPServiceStub.find.resolves(foundFTP);
        fTPServiceStub.retrieve.resolves([foundFTP]);

        // WHEN
        comp.beforeRouteEnter({ params: { fTPId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.fTP).toBe(foundFTP);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});
