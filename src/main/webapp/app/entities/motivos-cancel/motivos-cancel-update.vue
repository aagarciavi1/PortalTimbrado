<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="proyecto03App.motivosCancel.home.createOrEditLabel"
          data-cy="MotivosCancelCreateUpdateHeading"
          v-text="$t('proyecto03App.motivosCancel.home.createOrEditLabel')"
        >
          Create or edit a MotivosCancel
        </h2>
        <div>
          <div class="form-group" v-if="motivosCancel.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="motivosCancel.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.motivosCancel.fecha')" for="motivos-cancel-fecha">Fecha</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="motivos-cancel-fecha"
                  v-model="$v.motivosCancel.fecha.$model"
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
                id="motivos-cancel-fecha"
                data-cy="fecha"
                type="text"
                class="form-control"
                name="fecha"
                :class="{ valid: !$v.motivosCancel.fecha.$invalid, invalid: $v.motivosCancel.fecha.$invalid }"
                v-model="$v.motivosCancel.fecha.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.motivosCancel.motivoCancel')" for="motivos-cancel-motivoCancel"
              >Motivo Cancel</label
            >
            <input
              type="text"
              class="form-control"
              name="motivoCancel"
              id="motivos-cancel-motivoCancel"
              data-cy="motivoCancel"
              :class="{ valid: !$v.motivosCancel.motivoCancel.$invalid, invalid: $v.motivosCancel.motivoCancel.$invalid }"
              v-model="$v.motivosCancel.motivoCancel.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.motivosCancel.estatus')" for="motivos-cancel-estatus"
              >Estatus</label
            >
            <select
              class="form-control"
              name="estatus"
              :class="{ valid: !$v.motivosCancel.estatus.$invalid, invalid: $v.motivosCancel.estatus.$invalid }"
              v-model="$v.motivosCancel.estatus.$model"
              id="motivos-cancel-estatus"
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
            <label class="form-control-label" v-text="$t('proyecto03App.motivosCancel.usuario')" for="motivos-cancel-usuario"
              >Usuario</label
            >
            <input
              type="text"
              class="form-control"
              name="usuario"
              id="motivos-cancel-usuario"
              data-cy="usuario"
              :class="{ valid: !$v.motivosCancel.usuario.$invalid, invalid: $v.motivosCancel.usuario.$invalid }"
              v-model="$v.motivosCancel.usuario.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.motivosCancel.fechaMod')" for="motivos-cancel-fechaMod"
              >Fecha Mod</label
            >
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="motivos-cancel-fechaMod"
                  v-model="$v.motivosCancel.fechaMod.$model"
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
                id="motivos-cancel-fechaMod"
                data-cy="fechaMod"
                type="text"
                class="form-control"
                name="fechaMod"
                :class="{ valid: !$v.motivosCancel.fechaMod.$invalid, invalid: $v.motivosCancel.fechaMod.$invalid }"
                v-model="$v.motivosCancel.fechaMod.$model"
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
            :disabled="$v.motivosCancel.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./motivos-cancel-update.component.ts"></script>
