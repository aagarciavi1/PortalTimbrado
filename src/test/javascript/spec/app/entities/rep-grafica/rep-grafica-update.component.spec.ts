/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import RepGraficaUpdateComponent from '@/entities/rep-grafica/rep-grafica-update.vue';
import RepGraficaClass from '@/entities/rep-grafica/rep-grafica-update.component';
import RepGraficaService from '@/entities/rep-grafica/rep-grafica.service';

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
  describe('RepGrafica Management Update Component', () => {
    let wrapper: Wrapper<RepGraficaClass>;
    let comp: RepGraficaClass;
    let repGraficaServiceStub: SinonStubbedInstance<RepGraficaService>;

    beforeEach(() => {
      repGraficaServiceStub = sinon.createStubInstance<RepGraficaService>(RepGraficaService);

      wrapper = shallowMount<RepGraficaClass>(RepGraficaUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          repGraficaService: () => repGraficaServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.repGrafica = entity;
        repGraficaServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(repGraficaServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.repGrafica = entity;
        repGraficaServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(repGraficaServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundRepGrafica = { id: 123 };
        repGraficaServiceStub.find.resolves(foundRepGrafica);
        repGraficaServiceStub.retrieve.resolves([foundRepGrafica]);

        // WHEN
        comp.beforeRouteEnter({ params: { repGraficaId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.repGrafica).toBe(foundRepGrafica);
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
