/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import PerfilesUpdateComponent from '@/entities/perfiles/perfiles-update.vue';
import PerfilesClass from '@/entities/perfiles/perfiles-update.component';
import PerfilesService from '@/entities/perfiles/perfiles.service';

import FuncionalidadesService from '@/entities/funcionalidades/funcionalidades.service';
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
  describe('Perfiles Management Update Component', () => {
    let wrapper: Wrapper<PerfilesClass>;
    let comp: PerfilesClass;
    let perfilesServiceStub: SinonStubbedInstance<PerfilesService>;

    beforeEach(() => {
      perfilesServiceStub = sinon.createStubInstance<PerfilesService>(PerfilesService);

      wrapper = shallowMount<PerfilesClass>(PerfilesUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          perfilesService: () => perfilesServiceStub,
          alertService: () => new AlertService(),

          funcionalidadesService: () =>
            sinon.createStubInstance<FuncionalidadesService>(FuncionalidadesService, {
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
        comp.perfiles = entity;
        perfilesServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(perfilesServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.perfiles = entity;
        perfilesServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(perfilesServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundPerfiles = { id: 123 };
        perfilesServiceStub.find.resolves(foundPerfiles);
        perfilesServiceStub.retrieve.resolves([foundPerfiles]);

        // WHEN
        comp.beforeRouteEnter({ params: { perfilesId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.perfiles).toBe(foundPerfiles);
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
