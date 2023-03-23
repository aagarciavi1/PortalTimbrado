/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import FTPDetailComponent from '@/entities/ftp/ftp-details.vue';
import FTPClass from '@/entities/ftp/ftp-details.component';
import FTPService from '@/entities/ftp/ftp.service';
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
  describe('FTP Management Detail Component', () => {
    let wrapper: Wrapper<FTPClass>;
    let comp: FTPClass;
    let fTPServiceStub: SinonStubbedInstance<FTPService>;

    beforeEach(() => {
      fTPServiceStub = sinon.createStubInstance<FTPService>(FTPService);

      wrapper = shallowMount<FTPClass>(FTPDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { fTPService: () => fTPServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundFTP = { id: 123 };
        fTPServiceStub.find.resolves(foundFTP);

        // WHEN
        comp.retrieveFTP(123);
        await comp.$nextTick();

        // THEN
        expect(comp.fTP).toBe(foundFTP);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundFTP = { id: 123 };
        fTPServiceStub.find.resolves(foundFTP);

        // WHEN
        comp.beforeRouteEnter({ params: { fTPId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.fTP).toBe(foundFTP);
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
