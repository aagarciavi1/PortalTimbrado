/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import MotivosCancelUpdateComponent from '@/entities/motivos-cancel/motivos-cancel-update.vue';
import MotivosCancelClass from '@/entities/motivos-cancel/motivos-cancel-update.component';
import MotivosCancelService from '@/entities/motivos-cancel/motivos-cancel.service';

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
  describe('MotivosCancel Management Update Component', () => {
    let wrapper: Wrapper<MotivosCancelClass>;
    let comp: MotivosCancelClass;
    let motivosCancelServiceStub: SinonStubbedInstance<MotivosCancelService>;

    beforeEach(() => {
      motivosCancelServiceStub = sinon.createStubInstance<MotivosCancelService>(MotivosCancelService);

      wrapper = shallowMount<MotivosCancelClass>(MotivosCancelUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          motivosCancelService: () => motivosCancelServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.motivosCancel = entity;
        motivosCancelServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(motivosCancelServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.motivosCancel = entity;
        motivosCancelServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(motivosCancelServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundMotivosCancel = { id: 123 };
        motivosCancelServiceStub.find.resolves(foundMotivosCancel);
        motivosCancelServiceStub.retrieve.resolves([foundMotivosCancel]);

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
