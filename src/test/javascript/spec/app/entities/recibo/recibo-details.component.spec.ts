/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import ReciboDetailComponent from '@/entities/recibo/recibo-details.vue';
import ReciboClass from '@/entities/recibo/recibo-details.component';
import ReciboService from '@/entities/recibo/recibo.service';
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
  describe('Recibo Management Detail Component', () => {
    let wrapper: Wrapper<ReciboClass>;
    let comp: ReciboClass;
    let reciboServiceStub: SinonStubbedInstance<ReciboService>;

    beforeEach(() => {
      reciboServiceStub = sinon.createStubInstance<ReciboService>(ReciboService);

      wrapper = shallowMount<ReciboClass>(ReciboDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { reciboService: () => reciboServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundRecibo = { id: 123 };
        reciboServiceStub.find.resolves(foundRecibo);

        // WHEN
        comp.retrieveRecibo(123);
        await comp.$nextTick();

        // THEN
        expect(comp.recibo).toBe(foundRecibo);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundRecibo = { id: 123 };
        reciboServiceStub.find.resolves(foundRecibo);

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
