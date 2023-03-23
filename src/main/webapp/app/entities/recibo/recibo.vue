<template>
  <div>
    <h2 id="page-heading" data-cy="ReciboHeading">
      <span v-text="$t('proyecto03App.recibo.home.title')" id="recibo-heading">Recibos</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('proyecto03App.recibo.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'ReciboCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-recibo"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('proyecto03App.recibo.home.createLabel')"> Create a new Recibo </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && recibos && recibos.length === 0">
      <span v-text="$t('proyecto03App.recibo.home.notFound')">No recibos found</span>
    </div>
    <div class="table-responsive" v-if="recibos && recibos.length > 0">
      <table class="table table-striped" aria-describedby="recibos">
        <thead>
          <tr>
            <th scope="row" v-on:click="changeOrder('id')">
              <span v-text="$t('global.field.id')">ID</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('fecha')">
              <span v-text="$t('proyecto03App.recibo.fecha')">Fecha</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'fecha'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('idInstit')">
              <span v-text="$t('proyecto03App.recibo.idInstit')">Id Instit</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'idInstit'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('nivel')">
              <span v-text="$t('proyecto03App.recibo.nivel')">Nivel</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'nivel'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('tipoCFDI')">
              <span v-text="$t('proyecto03App.recibo.tipoCFDI')">Tipo CFDI</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'tipoCFDI'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('clave')">
              <span v-text="$t('proyecto03App.recibo.clave')">Clave</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'clave'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('envioConCFDI')">
              <span v-text="$t('proyecto03App.recibo.envioConCFDI')">Envio Con CFDI</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'envioConCFDI'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('envioSinCFDI')">
              <span v-text="$t('proyecto03App.recibo.envioSinCFDI')">Envio Sin CFDI</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'envioSinCFDI'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('aplicaLeyenda')">
              <span v-text="$t('proyecto03App.recibo.aplicaLeyenda')">Aplica Leyenda</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'aplicaLeyenda'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('leyenda')">
              <span v-text="$t('proyecto03App.recibo.leyenda')">Leyenda</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'leyenda'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('usuario')">
              <span v-text="$t('proyecto03App.recibo.usuario')">Usuario</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'usuario'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('fechaMod')">
              <span v-text="$t('proyecto03App.recibo.fechaMod')">Fecha Mod</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'fechaMod'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('tiporecibo.id')">
              <span v-text="$t('proyecto03App.recibo.tiporecibo')">Tiporecibo</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'tiporecibo.id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('repgrafica.id')">
              <span v-text="$t('proyecto03App.recibo.repgrafica')">Repgrafica</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'repgrafica.id'"></jhi-sort-indicator>
            </th>
            <th scope="row" v-on:click="changeOrder('parametros.id')">
              <span v-text="$t('proyecto03App.recibo.parametros')">Parametros</span>
              <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'parametros.id'"></jhi-sort-indicator>
            </th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="recibo in recibos" :key="recibo.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'ReciboView', params: { reciboId: recibo.id } }">{{ recibo.id }}</router-link>
            </td>
            <td>{{ recibo.fecha }}</td>
            <td>{{ recibo.idInstit }}</td>
            <td>{{ recibo.nivel }}</td>
            <td>{{ recibo.tipoCFDI }}</td>
            <td>{{ recibo.clave }}</td>
            <td>{{ recibo.envioConCFDI }}</td>
            <td>{{ recibo.envioSinCFDI }}</td>
            <td>{{ recibo.aplicaLeyenda }}</td>
            <td>{{ recibo.leyenda }}</td>
            <td>{{ recibo.usuario }}</td>
            <td>{{ recibo.fechaMod }}</td>
            <td>
              <div v-if="recibo.tiporecibo">
                <router-link :to="{ name: 'TipoReciboView', params: { tipoReciboId: recibo.tiporecibo.id } }">{{
                  recibo.tiporecibo.id
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="recibo.repgrafica">
                <router-link :to="{ name: 'RepGraficaView', params: { repGraficaId: recibo.repgrafica.id } }">{{
                  recibo.repgrafica.id
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="recibo.parametros">
                <router-link :to="{ name: 'ParametrosView', params: { parametrosId: recibo.parametros.id } }">{{
                  recibo.parametros.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'ReciboView', params: { reciboId: recibo.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'ReciboEdit', params: { reciboId: recibo.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(recibo)"
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
        ><span id="proyecto03App.recibo.delete.question" data-cy="reciboDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-recibo-heading" v-text="$t('proyecto03App.recibo.delete.question', { id: removeId })">
          Are you sure you want to delete this Recibo?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-recibo"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeRecibo()"
        >
          Delete
        </button>
      </div>
    </b-modal>
    <div v-show="recibos && recibos.length > 0">
      <div class="row justify-content-center">
        <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
      </div>
      <div class="row justify-content-center">
        <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./recibo.component.ts"></script>
