/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import ReciboUpdateComponent from '@/entities/recibo/recibo-update.vue';
import ReciboClass from '@/entities/recibo/recibo-update.component';
import ReciboService from '@/entities/recibo/recibo.service';

import TipoReciboService from '@/entities/tipo-recibo/tipo-recibo.service';

import RepGraficaService from '@/entities/rep-grafica/rep-grafica.service';

import ParametrosService from '@/entities/parametros/parametros.service';
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
  describe('Recibo Management Update Component', () => {
    let wrapper: Wrapper<ReciboClass>;
    let comp: ReciboClass;
    let reciboServiceStub: SinonStubbedInstance<ReciboService>;

    beforeEach(() => {
      reciboServiceStub = sinon.createStubInstance<ReciboService>(ReciboService);

      wrapper = shallowMount<ReciboClass>(ReciboUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          reciboService: () => reciboServiceStub,
          alertService: () => new AlertService(),

          tipoReciboService: () =>
            sinon.createStubInstance<TipoReciboService>(TipoReciboService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          repGraficaService: () =>
            sinon.createStubInstance<RepGraficaService>(RepGraficaService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          parametrosService: () =>
            sinon.createStubInstance<ParametrosService>(ParametrosService, {
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
        comp.recibo = entity;
        reciboServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(reciboServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.recibo = entity;
        reciboServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(reciboServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundRecibo = { id: 123 };
        reciboServiceStub.find.resolves(foundRecibo);
        reciboServiceStub.retrieve.resolves([foundRecibo]);

        // WHEN
        comp.beforeRouteEnter({ params: { reciboId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.recibo).toBe(foundRecibo);
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
