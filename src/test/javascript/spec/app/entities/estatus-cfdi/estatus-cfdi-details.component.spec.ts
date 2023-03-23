/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import EstatusCFDIDetailComponent from '@/entities/estatus-cfdi/estatus-cfdi-details.vue';
import EstatusCFDIClass from '@/entities/estatus-cfdi/estatus-cfdi-details.component';
import EstatusCFDIService from '@/entities/estatus-cfdi/estatus-cfdi.service';
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
  describe('EstatusCFDI Management Detail Component', () => {
    let wrapper: Wrapper<EstatusCFDIClass>;
    let comp: EstatusCFDIClass;
    let estatusCFDIServiceStub: SinonStubbedInstance<EstatusCFDIService>;

    beforeEach(() => {
      estatusCFDIServiceStub = sinon.createStubInstance<EstatusCFDIService>(EstatusCFDIService);

      wrapper = shallowMount<EstatusCFDIClass>(EstatusCFDIDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { estatusCFDIService: () => estatusCFDIServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundEstatusCFDI = { id: 123 };
        estatusCFDIServiceStub.find.resolves(foundEstatusCFDI);

        // WHEN
        comp.retrieveEstatusCFDI(123);
        await comp.$nextTick();

        // THEN
        expect(comp.estatusCFDI).toBe(foundEstatusCFDI);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundEstatusCFDI = { id: 123 };
        estatusCFDIServiceStub.find.resolves(foundEstatusCFDI);

        // WHEN
        comp.beforeRouteEnter({ params: { estatusCFDIId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.estatusCFDI).toBe(foundEstatusCFDI);
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
