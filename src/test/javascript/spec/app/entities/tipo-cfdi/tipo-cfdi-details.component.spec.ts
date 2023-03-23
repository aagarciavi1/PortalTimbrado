/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import TipoCFDIDetailComponent from '@/entities/tipo-cfdi/tipo-cfdi-details.vue';
import TipoCFDIClass from '@/entities/tipo-cfdi/tipo-cfdi-details.component';
import TipoCFDIService from '@/entities/tipo-cfdi/tipo-cfdi.service';
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
  describe('TipoCFDI Management Detail Component', () => {
    let wrapper: Wrapper<TipoCFDIClass>;
    let comp: TipoCFDIClass;
    let tipoCFDIServiceStub: SinonStubbedInstance<TipoCFDIService>;

    beforeEach(() => {
      tipoCFDIServiceStub = sinon.createStubInstance<TipoCFDIService>(TipoCFDIService);

      wrapper = shallowMount<TipoCFDIClass>(TipoCFDIDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { tipoCFDIService: () => tipoCFDIServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundTipoCFDI = { id: 123 };
        tipoCFDIServiceStub.find.resolves(foundTipoCFDI);

        // WHEN
        comp.retrieveTipoCFDI(123);
        await comp.$nextTick();

        // THEN
        expect(comp.tipoCFDI).toBe(foundTipoCFDI);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundTipoCFDI = { id: 123 };
        tipoCFDIServiceStub.find.resolves(foundTipoCFDI);

        // WHEN
        comp.beforeRouteEnter({ params: { tipoCFDIId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.tipoCFDI).toBe(foundTipoCFDI);
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
