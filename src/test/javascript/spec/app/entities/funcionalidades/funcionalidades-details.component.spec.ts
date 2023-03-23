/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import FuncionalidadesDetailComponent from '@/entities/funcionalidades/funcionalidades-details.vue';
import FuncionalidadesClass from '@/entities/funcionalidades/funcionalidades-details.component';
import FuncionalidadesService from '@/entities/funcionalidades/funcionalidades.service';
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
  describe('Funcionalidades Management Detail Component', () => {
    let wrapper: Wrapper<FuncionalidadesClass>;
    let comp: FuncionalidadesClass;
    let funcionalidadesServiceStub: SinonStubbedInstance<FuncionalidadesService>;

    beforeEach(() => {
      funcionalidadesServiceStub = sinon.createStubInstance<FuncionalidadesService>(FuncionalidadesService);

      wrapper = shallowMount<FuncionalidadesClass>(FuncionalidadesDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { funcionalidadesService: () => funcionalidadesServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundFuncionalidades = { id: 123 };
        funcionalidadesServiceStub.find.resolves(foundFuncionalidades);

        // WHEN
        comp.retrieveFuncionalidades(123);
        await comp.$nextTick();

        // THEN
        expect(comp.funcionalidades).toBe(foundFuncionalidades);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundFuncionalidades = { id: 123 };
        funcionalidadesServiceStub.find.resolves(foundFuncionalidades);

        // WHEN
        comp.beforeRouteEnter({ params: { funcionalidadesId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.funcionalidades).toBe(foundFuncionalidades);
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
