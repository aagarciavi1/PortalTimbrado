/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import FuncionalidadesUpdateComponent from '@/entities/funcionalidades/funcionalidades-update.vue';
import FuncionalidadesClass from '@/entities/funcionalidades/funcionalidades-update.component';
import FuncionalidadesService from '@/entities/funcionalidades/funcionalidades.service';

import PerfilesService from '@/entities/perfiles/perfiles.service';
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
  describe('Funcionalidades Management Update Component', () => {
    let wrapper: Wrapper<FuncionalidadesClass>;
    let comp: FuncionalidadesClass;
    let funcionalidadesServiceStub: SinonStubbedInstance<FuncionalidadesService>;

    beforeEach(() => {
      funcionalidadesServiceStub = sinon.createStubInstance<FuncionalidadesService>(FuncionalidadesService);

      wrapper = shallowMount<FuncionalidadesClass>(FuncionalidadesUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          funcionalidadesService: () => funcionalidadesServiceStub,
          alertService: () => new AlertService(),

          perfilesService: () =>
            sinon.createStubInstance<PerfilesService>(PerfilesService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.funcionalidades = entity;
        funcionalidadesServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(funcionalidadesServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.funcionalidades = entity;
        funcionalidadesServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(funcionalidadesServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundFuncionalidades = { id: 123 };
        funcionalidadesServiceStub.find.resolves(foundFuncionalidades);
        funcionalidadesServiceStub.retrieve.resolves([foundFuncionalidades]);

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
