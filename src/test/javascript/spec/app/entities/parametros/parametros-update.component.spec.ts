/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import ParametrosUpdateComponent from '@/entities/parametros/parametros-update.vue';
import ParametrosClass from '@/entities/parametros/parametros-update.component';
import ParametrosService from '@/entities/parametros/parametros.service';

import RegistInstitService from '@/entities/regist-instit/regist-instit.service';

import TipoCFDIService from '@/entities/tipo-cfdi/tipo-cfdi.service';

import ReciboService from '@/entities/recibo/recibo.service';
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
  describe('Parametros Management Update Component', () => {
    let wrapper: Wrapper<ParametrosClass>;
    let comp: ParametrosClass;
    let parametrosServiceStub: SinonStubbedInstance<ParametrosService>;

    beforeEach(() => {
      parametrosServiceStub = sinon.createStubInstance<ParametrosService>(ParametrosService);

      wrapper = shallowMount<ParametrosClass>(ParametrosUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          parametrosService: () => parametrosServiceStub,
          alertService: () => new AlertService(),

          registInstitService: () =>
            sinon.createStubInstance<RegistInstitService>(RegistInstitService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          tipoCFDIService: () =>
            sinon.createStubInstance<TipoCFDIService>(TipoCFDIService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          reciboService: () =>
            sinon.createStubInstance<ReciboService>(ReciboService, {
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
        comp.parametros = entity;
        parametrosServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(parametrosServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.parametros = entity;
        parametrosServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(parametrosServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundParametros = { id: 123 };
        parametrosServiceStub.find.resolves(foundParametros);
        parametrosServiceStub.retrieve.resolves([foundParametros]);

        // WHEN
        comp.beforeRouteEnter({ params: { parametrosId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.parametros).toBe(foundParametros);
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
