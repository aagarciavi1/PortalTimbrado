<template>
  <div>
    <h2 id="page-heading" data-cy="NotificacionesHeading">
      <span v-text="$t('proyecto03App.notificaciones.home.title')" id="notificaciones-heading">Notificaciones</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('proyecto03App.notificaciones.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'NotificacionesCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-notificaciones"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('proyecto03App.notificaciones.home.createLabel')"> Create a new Notificaciones </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && notificaciones && notificaciones.length === 0">
      <span v-text="$t('proyecto03App.notificaciones.home.notFound')">No notificaciones found</span>
    </div>
    <div class="table-responsive" v-if="notificaciones && notificaciones.length > 0">
      <table class="table table-striped" aria-describedby="notificaciones">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="$t('global.field.id')">ID</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('fecha')">
              <span v-text="$t('proyecto03App.notificaciones.fecha')">Fecha</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'fecha'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('idInstit')">
              <span v-text="$t('proyecto03App.notificaciones.idInstit')">Id Instit</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'idInstit'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('nivel')">
              <span v-text="$t('proyecto03App.notificaciones.nivel')">Nivel</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'nivel'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('tipoNot')">
              <span v-text="$t('proyecto03App.notificaciones.tipoNot')">Tipo Not</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'tipoNot'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('clave')">
              <span v-text="$t('proyecto03App.notificaciones.clave')">Clave</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'clave'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('asunto')">
              <span v-text="$t('proyecto03App.notificaciones.asunto')">Asunto</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'asunto'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('texto')">
              <span v-text="$t('proyecto03App.notificaciones.texto')">Texto</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'texto'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('piePagina')">
              <span v-text="$t('proyecto03App.notificaciones.piePagina')">Pie Pagina</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'piePagina'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('usuario')">
              <span v-text="$t('proyecto03App.notificaciones.usuario')">Usuario</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'usuario'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('fechaMod')">
              <span v-text="$t('proyecto03App.notificaciones.fechaMod')">Fecha Mod</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'fechaMod'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('registinstit.id')">
              <span v-text="$t('proyecto03App.notificaciones.registinstit')">Registinstit</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'registinstit.id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('tipocfdi.id')">
              <span v-text="$t('proyecto03App.notificaciones.tipocfdi')">Tipocfdi</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'tipocfdi.id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('tiporecibo.id')">
              <span v-text="$t('proyecto03App.notificaciones.tiporecibo')">Tiporecibo</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'tiporecibo.id'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="notificaciones in notificaciones" :key="notificaciones.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'NotificacionesView', params: { notificacionesId: notificaciones.id } }">{{
                notificaciones.id
              }}</router-link>
            </td>
            <td>{{ notificaciones.fecha }}</td>
            <td>{{ notificaciones.idInstit }}</td>
            <td>{{ notificaciones.nivel }}</td>
            <td v-text="$t('proyecto03App.TipoNot.' + notificaciones.tipoNot)">{{ notificaciones.tipoNot }}</td>
            <td>{{ notificaciones.clave }}</td>
            <td>{{ notificaciones.asunto }}</td>
            <td>{{ notificaciones.texto }}</td>
            <td>{{ notificaciones.piePagina }}</td>
            <td>{{ notificaciones.usuario }}</td>
            <td>{{ notificaciones.fechaMod }}</td>
            <td>
              <div v-if="notificaciones.registinstit">
                <router-link :to="{ name: 'RegistInstitView', params: { registInstitId: notificaciones.registinstit.id } }">{{
                  notificaciones.registinstit.id
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="notificaciones.tipocfdi">
                <router-link :to="{ name: 'TipoCFDIView', params: { tipoCFDIId: notificaciones.tipocfdi.id } }">{{
                  notificaciones.tipocfdi.id
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="notificaciones.tiporecibo">
                <router-link :to="{ name: 'TipoReciboView', params: { tipoReciboId: notificaciones.tiporecibo.id } }">{{
                  notificaciones.tiporecibo.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link
                  :to="{ name: 'NotificacionesView', params: { notificacionesId: notificaciones.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link
                  :to="{ name: 'NotificacionesEdit', params: { notificacionesId: notificaciones.id } }"
                  custom
                  v-slot="{ navigate }"
                >
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(notificaciones)"
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
        ><span
          id="proyecto03App.notificaciones.delete.question"
          data-cy="notificacionesDeleteDialogHeading"
          v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-notificaciones-heading" v-text="$t('proyecto03App.notificaciones.delete.question', { id: removeId })">
          Are you sure you want to delete this Notificaciones?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-notificaciones"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeNotificaciones()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="notificaciones && notificaciones.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./notificaciones.component.ts"></script>
