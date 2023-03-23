/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import RegimenFisUpdateComponent from '@/entities/regimen-fis/regimen-fis-update.vue';
import RegimenFisClass from '@/entities/regimen-fis/regimen-fis-update.component';
import RegimenFisService from '@/entities/regimen-fis/regimen-fis.service';

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
  describe('RegimenFis Management Update Component', () => {
    let wrapper: Wrapper<RegimenFisClass>;
    let comp: RegimenFisClass;
    let regimenFisServiceStub: SinonStubbedInstance<RegimenFisService>;

    beforeEach(() => {
      regimenFisServiceStub = sinon.createStubInstance<RegimenFisService>(RegimenFisService);

      wrapper = shallowMount<RegimenFisClass>(RegimenFisUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          regimenFisService: () => regimenFisServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.regimenFis = entity;
        regimenFisServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(regimenFisServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.regimenFis = entity;
        regimenFisServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(regimenFisServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundRegimenFis = { id: 123 };
        regimenFisServiceStub.find.resolves(foundRegimenFis);
        regimenFisServiceStub.retrieve.resolves([foundRegimenFis]);

        // WHEN
        comp.beforeRouteEnter({ params: { regimenFisId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.regimenFis).toBe(foundRegimenFis);
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
