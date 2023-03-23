/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import RepGraficaDetailComponent from '@/entities/rep-grafica/rep-grafica-details.vue';
import RepGraficaClass from '@/entities/rep-grafica/rep-grafica-details.component';
import RepGraficaService from '@/entities/rep-grafica/rep-grafica.service';
import router from '@/router';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('RepGrafica Management Detail Component', () => {
    let wrapper: Wrapper<RepGraficaClass>;
    let comp: RepGraficaClass;
    let repGraficaServiceStub: SinonStubbedInstance<RepGraficaService>;

    beforeEach(() => {
      repGraficaServiceStub = sinon.createStubInstance<RepGraficaService>(RepGraficaService);

      wrapper = shallowMount<RepGraficaClass>(RepGraficaDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { repGraficaService: () => repGraficaServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundRepGrafica = { id: 123 };
        repGraficaServiceStub.find.resolves(foundRepGrafica);

        // WHEN
        comp.retrieveRepGrafica(123);
        await comp.$nextTick();

        // THEN
        expect(comp.repGrafica).toBe(foundRepGrafica);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundRepGrafica = { id: 123 };
        repGraficaServiceStub.find.resolves(foundRepGrafica);

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
