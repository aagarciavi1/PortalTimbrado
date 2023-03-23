<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="proyecto03App.estatusCFDI.home.createOrEditLabel"
          data-cy="EstatusCFDICreateUpdateHeading"
          v-text="$t('proyecto03App.estatusCFDI.home.createOrEditLabel')"
        >
          Create or edit a EstatusCFDI
        </h2>
        <div>
          <div class="form-group" v-if="estatusCFDI.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="estatusCFDI.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.estatusCFDI.fecha')" for="estatus-cfdi-fecha">Fecha</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="estatus-cfdi-fecha"
                  v-model="$v.estatusCFDI.fecha.$model"
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
                id="estatus-cfdi-fecha"
                data-cy="fecha"
                type="text"
                class="form-control"
                name="fecha"
                :class="{ valid: !$v.estatusCFDI.fecha.$invalid, invalid: $v.estatusCFDI.fecha.$invalid }"
                v-model="$v.estatusCFDI.fecha.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.estatusCFDI.descEstCFDI')" for="estatus-cfdi-descEstCFDI"
              >Desc Est CFDI</label
            >
            <input
              type="text"
              class="form-control"
              name="descEstCFDI"
              id="estatus-cfdi-descEstCFDI"
              data-cy="descEstCFDI"
              :class="{ valid: !$v.estatusCFDI.descEstCFDI.$invalid, invalid: $v.estatusCFDI.descEstCFDI.$invalid }"
              v-model="$v.estatusCFDI.descEstCFDI.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.estatusCFDI.estatus')" for="estatus-cfdi-estatus">Estatus</label>
            <select
              class="form-control"
              name="estatus"
              :class="{ valid: !$v.estatusCFDI.estatus.$invalid, invalid: $v.estatusCFDI.estatus.$invalid }"
              v-model="$v.estatusCFDI.estatus.$model"
              id="estatus-cfdi-estatus"
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
            <label class="form-control-label" v-text="$t('proyecto03App.estatusCFDI.usuario')" for="estatus-cfdi-usuario">Usuario</label>
            <input
              type="text"
              class="form-control"
              name="usuario"
              id="estatus-cfdi-usuario"
              data-cy="usuario"
              :class="{ valid: !$v.estatusCFDI.usuario.$invalid, invalid: $v.estatusCFDI.usuario.$invalid }"
              v-model="$v.estatusCFDI.usuario.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.estatusCFDI.fechaMod')" for="estatus-cfdi-fechaMod"
              >Fecha Mod</label
            >
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="estatus-cfdi-fechaMod"
                  v-model="$v.estatusCFDI.fechaMod.$model"
                  name="fechaMod"
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
                id="estatus-cfdi-fechaMod"
                data-cy="fechaMod"
                type="text"
                class="form-control"
                name="fechaMod"
                :class="{ valid: !$v.estatusCFDI.fechaMod.$invalid, invalid: $v.estatusCFDI.fechaMod.$invalid }"
                v-model="$v.estatusCFDI.fechaMod.$model"
              />
            </b-input-group>
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
            :disabled="$v.estatusCFDI.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./estatus-cfdi-update.component.ts"></script>
