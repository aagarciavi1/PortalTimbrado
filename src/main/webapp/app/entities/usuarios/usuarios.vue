<template>
  <div>
    <h2 id="page-heading" data-cy="UsuariosHeading">
      <span v-text="$t('proyecto03App.usuarios.home.title')" id="usuarios-heading">Usuarios</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('proyecto03App.usuarios.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'UsuariosCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-usuarios"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('proyecto03App.usuarios.home.createLabel')"> Create a new Usuarios </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && usuarios && usuarios.length === 0">
      <span v-text="$t('proyecto03App.usuarios.home.notFound')">No usuarios found</span>
    </div>
    <div class="table-responsive" v-if="usuarios && usuarios.length > 0">
      <table class="table table-striped" aria-describedby="usuarios">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="$t('global.field.id')">ID</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('fecha')">
              <span v-text="$t('proyecto03App.usuarios.fecha')">Fecha</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'fecha'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('usuario')">
              <span v-text="$t('proyecto03App.usuarios.usuario')">Usuario</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'usuario'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('descr')">
              <span v-text="$t('proyecto03App.usuarios.descr')">Descr</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'descr'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('idInstit')">
              <span v-text="$t('proyecto03App.usuarios.idInstit')">Id Instit</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'idInstit'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('correo')">
              <span v-text="$t('proyecto03App.usuarios.correo')">Correo</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'correo'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('contrasena')">
              <span v-text="$t('proyecto03App.usuarios.contrasena')">Contrasena</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'contrasena'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('confirmarContras')">
              <span v-text="$t('proyecto03App.usuarios.confirmarContras')">Confirmar Contras</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'confirmarContras'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('estatus')">
              <span v-text="$t('proyecto03App.usuarios.estatus')">Estatus</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'estatus'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('usuarioCrea')">
              <span v-text="$t('proyecto03App.usuarios.usuarioCrea')">Usuario Crea</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'usuarioCrea'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('registinstit.id')">
              <span v-text="$t('proyecto03App.usuarios.registinstit')">Registinstit</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'registinstit.id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('perfil.id')">
              <span v-text="$t('proyecto03App.usuarios.perfil')">Perfil</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'perfil.id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('tipocfdi.id')">
              <span v-text="$t('proyecto03App.usuarios.tipocfdi')">Tipocfdi</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'tipocfdi.id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('tiporecibo.id')">
              <span v-text="$t('proyecto03App.usuarios.tiporecibo')">Tiporecibo</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'tiporecibo.id'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="usuarios in usuarios" :key="usuarios.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'UsuariosView', params: { usuariosId: usuarios.id } }">{{ usuarios.id }}</router-link>
            </td>
            <td>{{ usuarios.fecha }}</td>
            <td>{{ usuarios.usuario }}</td>
            <td>{{ usuarios.descr }}</td>
            <td>{{ usuarios.idInstit }}</td>
            <td>{{ usuarios.correo }}</td>
            <td>{{ usuarios.contrasena }}</td>
            <td>{{ usuarios.confirmarContras }}</td>
            <td v-text="$t('proyecto03App.Estatus.' + usuarios.estatus)">{{ usuarios.estatus }}</td>
            <td>{{ usuarios.usuarioCrea }}</td>
            <td>
              <div v-if="usuarios.registinstit">
                <router-link :to="{ name: 'RegistInstitView', params: { registInstitId: usuarios.registinstit.id } }">{{
                  usuarios.registinstit.id
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="usuarios.perfil">
                <router-link :to="{ name: 'PerfilesView', params: { perfilesId: usuarios.perfil.id } }">{{
                  usuarios.perfil.id
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="usuarios.tipocfdi">
                <router-link :to="{ name: 'TipoCFDIView', params: { tipoCFDIId: usuarios.tipocfdi.id } }">{{
                  usuarios.tipocfdi.id
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="usuarios.tiporecibo">
                <router-link :to="{ name: 'TipoReciboView', params: { tipoReciboId: usuarios.tiporecibo.id } }">{{
                  usuarios.tiporecibo.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'UsuariosView', params: { usuariosId: usuarios.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'UsuariosEdit', params: { usuariosId: usuarios.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(usuarios)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span id="proyecto03App.usuarios.delete.question" data-cy="usuariosDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-usuarios-heading" v-text="$t('proyecto03App.usuarios.delete.question', { id: removeId })">
          Are you sure you want to delete this Usuarios?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-usuarios"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeUsuarios()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="usuarios && usuarios.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./usuarios.component.ts"></script>
