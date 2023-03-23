/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import UsuariosDetailComponent from '@/entities/usuarios/usuarios-details.vue';
import UsuariosClass from '@/entities/usuarios/usuarios-details.component';
import UsuariosService from '@/entities/usuarios/usuarios.service';
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
  describe('Usuarios Management Detail Component', () => {
    let wrapper: Wrapper<UsuariosClass>;
    let comp: UsuariosClass;
    let usuariosServiceStub: SinonStubbedInstance<UsuariosService>;

    beforeEach(() => {
      usuariosServiceStub = sinon.createStubInstance<UsuariosService>(UsuariosService);

      wrapper = shallowMount<UsuariosClass>(UsuariosDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { usuariosService: () => usuariosServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundUsuarios = { id: 123 };
        usuariosServiceStub.find.resolves(foundUsuarios);

        // WHEN
        comp.retrieveUsuarios(123);
        await comp.$nextTick();

        // THEN
        expect(comp.usuarios).toBe(foundUsuarios);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundUsuarios = { id: 123 };
        usuariosServiceStub.find.resolves(foundUsuarios);

        // WHEN
        comp.beforeRouteEnter({ params: { usuariosId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.usuarios).toBe(foundUsuarios);
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
