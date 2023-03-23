/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import ParametrosDetailComponent from '@/entities/parametros/parametros-details.vue';
import ParametrosClass from '@/entities/parametros/parametros-details.component';
import ParametrosService from '@/entities/parametros/parametros.service';
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
  describe('Parametros Management Detail Component', () => {
    let wrapper: Wrapper<ParametrosClass>;
    let comp: ParametrosClass;
    let parametrosServiceStub: SinonStubbedInstance<ParametrosService>;

    beforeEach(() => {
      parametrosServiceStub = sinon.createStubInstance<ParametrosService>(ParametrosService);

      wrapper = shallowMount<ParametrosClass>(ParametrosDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { parametrosService: () => parametrosServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundParametros = { id: 123 };
        parametrosServiceStub.find.resolves(foundParametros);

        // WHEN
        comp.retrieveParametros(123);
        await comp.$nextTick();

        // THEN
        expect(comp.parametros).toBe(foundParametros);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundParametros = { id: 123 };
        parametrosServiceStub.find.resolves(foundParametros);

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
