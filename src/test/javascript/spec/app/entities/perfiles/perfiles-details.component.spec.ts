/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import PerfilesDetailComponent from '@/entities/perfiles/perfiles-details.vue';
import PerfilesClass from '@/entities/perfiles/perfiles-details.component';
import PerfilesService from '@/entities/perfiles/perfiles.service';
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
  describe('Perfiles Management Detail Component', () => {
    let wrapper: Wrapper<PerfilesClass>;
    let comp: PerfilesClass;
    let perfilesServiceStub: SinonStubbedInstance<PerfilesService>;

    beforeEach(() => {
      perfilesServiceStub = sinon.createStubInstance<PerfilesService>(PerfilesService);

      wrapper = shallowMount<PerfilesClass>(PerfilesDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { perfilesService: () => perfilesServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundPerfiles = { id: 123 };
        perfilesServiceStub.find.resolves(foundPerfiles);

        // WHEN
        comp.retrievePerfiles(123);
        await comp.$nextTick();

        // THEN
        expect(comp.perfiles).toBe(foundPerfiles);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundPerfiles = { id: 123 };
        perfilesServiceStub.find.resolves(foundPerfiles);

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
