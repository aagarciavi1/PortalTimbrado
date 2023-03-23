/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import MotivosCancelDetailComponent from '@/entities/motivos-cancel/motivos-cancel-details.vue';
import MotivosCancelClass from '@/entities/motivos-cancel/motivos-cancel-details.component';
import MotivosCancelService from '@/entities/motivos-cancel/motivos-cancel.service';
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
  describe('MotivosCancel Management Detail Component', () => {
    let wrapper: Wrapper<MotivosCancelClass>;
    let comp: MotivosCancelClass;
    let motivosCancelServiceStub: SinonStubbedInstance<MotivosCancelService>;

    beforeEach(() => {
      motivosCancelServiceStub = sinon.createStubInstance<MotivosCancelService>(MotivosCancelService);

      wrapper = shallowMount<MotivosCancelClass>(MotivosCancelDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { motivosCancelService: () => motivosCancelServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundMotivosCancel = { id: 123 };
        motivosCancelServiceStub.find.resolves(foundMotivosCancel);

        // WHEN
        comp.retrieveMotivosCancel(123);
        await comp.$nextTick();

        // THEN
        expect(comp.motivosCancel).toBe(foundMotivosCancel);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundMotivosCancel = { id: 123 };
        motivosCancelServiceStub.find.resolves(foundMotivosCancel);

        // WHEN
        comp.beforeRouteEnter({ params: { motivosCancelId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.motivosCancel).toBe(foundMotivosCancel);
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
