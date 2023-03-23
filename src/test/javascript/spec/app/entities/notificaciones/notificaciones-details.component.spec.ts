/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import NotificacionesDetailComponent from '@/entities/notificaciones/notificaciones-details.vue';
import NotificacionesClass from '@/entities/notificaciones/notificaciones-details.component';
import NotificacionesService from '@/entities/notificaciones/notificaciones.service';
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
  describe('Notificaciones Management Detail Component', () => {
    let wrapper: Wrapper<NotificacionesClass>;
    let comp: NotificacionesClass;
    let notificacionesServiceStub: SinonStubbedInstance<NotificacionesService>;

    beforeEach(() => {
      notificacionesServiceStub = sinon.createStubInstance<NotificacionesService>(NotificacionesService);

      wrapper = shallowMount<NotificacionesClass>(NotificacionesDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { notificacionesService: () => notificacionesServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundNotificaciones = { id: 123 };
        notificacionesServiceStub.find.resolves(foundNotificaciones);

        // WHEN
        comp.retrieveNotificaciones(123);
        await comp.$nextTick();

        // THEN
        expect(comp.notificaciones).toBe(foundNotificaciones);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundNotificaciones = { id: 123 };
        notificacionesServiceStub.find.resolves(foundNotificaciones);

        // WHEN
        comp.beforeRouteEnter({ params: { notificacionesId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.notificaciones).toBe(foundNotificaciones);
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
