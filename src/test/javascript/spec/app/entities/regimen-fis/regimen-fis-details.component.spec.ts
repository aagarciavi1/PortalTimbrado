/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import RegimenFisDetailComponent from '@/entities/regimen-fis/regimen-fis-details.vue';
import RegimenFisClass from '@/entities/regimen-fis/regimen-fis-details.component';
import RegimenFisService from '@/entities/regimen-fis/regimen-fis.service';
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
  describe('RegimenFis Management Detail Component', () => {
    let wrapper: Wrapper<RegimenFisClass>;
    let comp: RegimenFisClass;
    let regimenFisServiceStub: SinonStubbedInstance<RegimenFisService>;

    beforeEach(() => {
      regimenFisServiceStub = sinon.createStubInstance<RegimenFisService>(RegimenFisService);

      wrapper = shallowMount<RegimenFisClass>(RegimenFisDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { regimenFisService: () => regimenFisServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundRegimenFis = { id: 123 };
        regimenFisServiceStub.find.resolves(foundRegimenFis);

        // WHEN
        comp.retrieveRegimenFis(123);
        await comp.$nextTick();

        // THEN
        expect(comp.regimenFis).toBe(foundRegimenFis);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundRegimenFis = { id: 123 };
        regimenFisServiceStub.find.resolves(foundRegimenFis);

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
