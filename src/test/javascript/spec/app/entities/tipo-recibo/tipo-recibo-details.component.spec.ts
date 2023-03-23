/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import TipoReciboDetailComponent from '@/entities/tipo-recibo/tipo-recibo-details.vue';
import TipoReciboClass from '@/entities/tipo-recibo/tipo-recibo-details.component';
import TipoReciboService from '@/entities/tipo-recibo/tipo-recibo.service';
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
  describe('TipoRecibo Management Detail Component', () => {
    let wrapper: Wrapper<TipoReciboClass>;
    let comp: TipoReciboClass;
    let tipoReciboServiceStub: SinonStubbedInstance<TipoReciboService>;

    beforeEach(() => {
      tipoReciboServiceStub = sinon.createStubInstance<TipoReciboService>(TipoReciboService);

      wrapper = shallowMount<TipoReciboClass>(TipoReciboDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { tipoReciboService: () => tipoReciboServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundTipoRecibo = { id: 123 };
        tipoReciboServiceStub.find.resolves(foundTipoRecibo);

        // WHEN
        comp.retrieveTipoRecibo(123);
        await comp.$nextTick();

        // THEN
        expect(comp.tipoRecibo).toBe(foundTipoRecibo);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundTipoRecibo = { id: 123 };
        tipoReciboServiceStub.find.resolves(foundTipoRecibo);

        // WHEN
        comp.beforeRouteEnter({ params: { tipoReciboId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.tipoRecibo).toBe(foundTipoRecibo);
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
