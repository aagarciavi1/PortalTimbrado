<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="proyecto03App.regimenFis.home.createOrEditLabel"
          data-cy="RegimenFisCreateUpdateHeading"
          v-text="$t('proyecto03App.regimenFis.home.createOrEditLabel')"
        >
          Create or edit a RegimenFis
        </h2>
        <div>
          <div class="form-group" v-if="regimenFis.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="regimenFis.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.regimenFis.fecha')" for="regimen-fis-fecha">Fecha</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="regimen-fis-fecha"
                  v-model="$v.regimenFis.fecha.$model"
                  name="fecha"
                  class="form-control"
                  :locale="currentLanguage"
                  button-only
                  today-button
                  reset-button
                  close-button
                >
                </b-form-datepicker>
              </b-input-group-prepend>
              <b-form-input
                id="regimen-fis-fecha"
                data-cy="fecha"
                type="text"
                class="form-control"
                name="fecha"
                :class="{ valid: !$v.regimenFis.fecha.$invalid, invalid: $v.regimenFis.fecha.$invalid }"
                v-model="$v.regimenFis.fecha.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.regimenFis.regimenF')" for="regimen-fis-regimenF">Regimen F</label>
            <input
              type="text"
              class="form-control"
              name="regimenF"
              id="regimen-fis-regimenF"
              data-cy="regimenF"
              :class="{ valid: !$v.regimenFis.regimenF.$invalid, invalid: $v.regimenFis.regimenF.$invalid }"
              v-model="$v.regimenFis.regimenF.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.regimenFis.estatus')" for="regimen-fis-estatus">Estatus</label>
            <select
              class="form-control"
              name="estatus"
              :class="{ valid: !$v.regimenFis.estatus.$invalid, invalid: $v.regimenFis.estatus.$invalid }"
              v-model="$v.regimenFis.estatus.$model"
              id="regimen-fis-estatus"
              data-cy="estatus"
            >
              <option
                v-for="estatus in estatusValues"
                :key="estatus"
                v-bind:value="estatus"
                v-bind:label="$t('proyecto03App.Estatus.' + estatus)"
              >
                {{ estatus }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.regimenFis.usuario')" for="regimen-fis-usuario">Usuario</label>
            <input
              type="text"
              class="form-control"
              name="usuario"
              id="regimen-fis-usuario"
              data-cy="usuario"
              :class="{ valid: !$v.regimenFis.usuario.$invalid, invalid: $v.regimenFis.usuario.$invalid }"
              v-model="$v.regimenFis.usuario.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.regimenFis.fechaMod')" for="regimen-fis-fechaMod">Fecha Mod</label>
            <input
              type="text"
              class="form-control"
              name="fechaMod"
              id="regimen-fis-fechaMod"
              data-cy="fechaMod"
              :class="{ valid: !$v.regimenFis.fechaMod.$invalid, invalid: $v.regimenFis.fechaMod.$invalid }"
              v-model="$v.regimenFis.fechaMod.$model"
            />
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.regimenFis.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./regimen-fis-update.component.ts"></script>
