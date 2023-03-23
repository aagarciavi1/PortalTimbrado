/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import RegistInstitDetailComponent from '@/entities/regist-instit/regist-instit-details.vue';
import RegistInstitClass from '@/entities/regist-instit/regist-instit-details.component';
import RegistInstitService from '@/entities/regist-instit/regist-instit.service';
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
  describe('RegistInstit Management Detail Component', () => {
    let wrapper: Wrapper<RegistInstitClass>;
    let comp: RegistInstitClass;
    let registInstitServiceStub: SinonStubbedInstance<RegistInstitService>;

    beforeEach(() => {
      registInstitServiceStub = sinon.createStubInstance<RegistInstitService>(RegistInstitService);

      wrapper = shallowMount<RegistInstitClass>(RegistInstitDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { registInstitService: () => registInstitServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundRegistInstit = { id: 123 };
        registInstitServiceStub.find.resolves(foundRegistInstit);

        // WHEN
        comp.retrieveRegistInstit(123);
        await comp.$nextTick();

        // THEN
        expect(comp.registInstit).toBe(foundRegistInstit);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundRegistInstit = { id: 123 };
        registInstitServiceStub.find.resolves(foundRegistInstit);

        // WHEN
        comp.beforeRouteEnter({ params: { registInstitId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.registInstit).toBe(foundRegistInstit);
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
