/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import UsuariosUpdateComponent from '@/entities/usuarios/usuarios-update.vue';
import UsuariosClass from '@/entities/usuarios/usuarios-update.component';
import UsuariosService from '@/entities/usuarios/usuarios.service';

import RegistInstitService from '@/entities/regist-instit/regist-instit.service';

import PerfilesService from '@/entities/perfiles/perfiles.service';

import TipoCFDIService from '@/entities/tipo-cfdi/tipo-cfdi.service';

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
  describe('Usuarios Management Update Component', () => {
    let wrapper: Wrapper<UsuariosClass>;
    let comp: UsuariosClass;
    let usuariosServiceStub: SinonStubbedInstance<UsuariosService>;

    beforeEach(() => {
      usuariosServiceStub = sinon.createStubInstance<UsuariosService>(UsuariosService);

      wrapper = shallowMount<UsuariosClass>(UsuariosUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          usuariosService: () => usuariosServiceStub,
          alertService: () => new AlertService(),

          registInstitService: () =>
            sinon.createStubInstance<RegistInstitService>(RegistInstitService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          perfilesService: () =>
            sinon.createStubInstance<PerfilesService>(PerfilesService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          tipoCFDIService: () =>
            sinon.createStubInstance<TipoCFDIService>(TipoCFDIService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          tipoReciboService: () =>
            sinon.createStubInstance<TipoReciboService>(TipoReciboService, {
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
        comp.usuarios = entity;
        usuariosServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(usuariosServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.usuarios = entity;
        usuariosServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(usuariosServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundUsuarios = { id: 123 };
        usuariosServiceStub.find.resolves(foundUsuarios);
        usuariosServiceStub.retrieve.resolves([foundUsuarios]);

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
