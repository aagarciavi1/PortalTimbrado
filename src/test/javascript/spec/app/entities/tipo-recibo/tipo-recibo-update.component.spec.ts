/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import TipoReciboUpdateComponent from '@/entities/tipo-recibo/tipo-recibo-update.vue';
import TipoReciboClass from '@/entities/tipo-recibo/tipo-recibo-update.component';
import TipoReciboService from '@/entities/tipo-recibo/tipo-recibo.service';

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
  describe('TipoRecibo Management Update Component', () => {
    let wrapper: Wrapper<TipoReciboClass>;
    let comp: TipoReciboClass;
    let tipoReciboServiceStub: SinonStubbedInstance<TipoReciboService>;

    beforeEach(() => {
      tipoReciboServiceStub = sinon.createStubInstance<TipoReciboService>(TipoReciboService);

      wrapper = shallowMount<TipoReciboClass>(TipoReciboUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          tipoReciboService: () => tipoReciboServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.tipoRecibo = entity;
        tipoReciboServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(tipoReciboServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.tipoRecibo = entity;
        tipoReciboServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(tipoReciboServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundTipoRecibo = { id: 123 };
        tipoReciboServiceStub.find.resolves(foundTipoRecibo);
        tipoReciboServiceStub.retrieve.resolves([foundTipoRecibo]);

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
