<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="proyecto03App.parametros.home.createOrEditLabel"
          data-cy="ParametrosCreateUpdateHeading"
          v-text="$t('proyecto03App.parametros.home.createOrEditLabel')"
        >
          Create or edit a Parametros
        </h2>
        <div>
          <div class="form-group" v-if="parametros.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="parametros.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.parametros.fecha')" for="parametros-fecha">Fecha</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="parametros-fecha"
                  v-model="$v.parametros.fecha.$model"
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
                id="parametros-fecha"
                data-cy="fecha"
                type="text"
                class="form-control"
                name="fecha"
                :class="{ valid: !$v.parametros.fecha.$invalid, invalid: $v.parametros.fecha.$invalid }"
                v-model="$v.parametros.fecha.$model"
                required
              />
            </b-input-group>
            <div v-if="$v.parametros.fecha.$anyDirty && $v.parametros.fecha.$invalid">
              <small class="form-text text-danger" v-if="!$v.parametros.fecha.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.parametros.idInstit')" for="parametros-idInstit">Id Instit</label>
            <input
              type="number"
              class="form-control"
              name="idInstit"
              id="parametros-idInstit"
              data-cy="idInstit"
              :class="{ valid: !$v.parametros.idInstit.$invalid, invalid: $v.parametros.idInstit.$invalid }"
              v-model.number="$v.parametros.idInstit.$model"
              required
            />
            <div v-if="$v.parametros.idInstit.$anyDirty && $v.parametros.idInstit.$invalid">
              <small class="form-text text-danger" v-if="!$v.parametros.idInstit.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.parametros.idInstit.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.parametros.nivel')" for="parametros-nivel">Nivel</label>
            <input
              type="number"
              class="form-control"
              name="nivel"
              id="parametros-nivel"
              data-cy="nivel"
              :class="{ valid: !$v.parametros.nivel.$invalid, invalid: $v.parametros.nivel.$invalid }"
              v-model.number="$v.parametros.nivel.$model"
              required
            />
            <div v-if="$v.parametros.nivel.$anyDirty && $v.parametros.nivel.$invalid">
              <small class="form-text text-danger" v-if="!$v.parametros.nivel.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.parametros.nivel.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.parametros.usuario')" for="parametros-usuario">Usuario</label>
            <input
              type="text"
              class="form-control"
              name="usuario"
              id="parametros-usuario"
              data-cy="usuario"
              :class="{ valid: !$v.parametros.usuario.$invalid, invalid: $v.parametros.usuario.$invalid }"
              v-model="$v.parametros.usuario.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.parametros.fechaMod')" for="parametros-fechaMod">Fecha Mod</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="parametros-fechaMod"
                  v-model="$v.parametros.fechaMod.$model"
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
                id="parametros-fechaMod"
                data-cy="fechaMod"
                type="text"
                class="form-control"
                name="fechaMod"
                :class="{ valid: !$v.parametros.fechaMod.$invalid, invalid: $v.parametros.fechaMod.$invalid }"
                v-model="$v.parametros.fechaMod.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.parametros.registinstit')" for="parametros-registinstit"
              >Registinstit</label
            >
            <select
              class="form-control"
              id="parametros-registinstit"
              data-cy="registinstit"
              name="registinstit"
              v-model="parametros.registinstit"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  parametros.registinstit && registInstitOption.id === parametros.registinstit.id
                    ? parametros.registinstit
                    : registInstitOption
                "
                v-for="registInstitOption in registInstits"
                :key="registInstitOption.id"
              >
                {{ registInstitOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.parametros.tipocfdi')" for="parametros-tipocfdi">Tipocfdi</label>
            <select class="form-control" id="parametros-tipocfdi" data-cy="tipocfdi" name="tipocfdi" v-model="parametros.tipocfdi">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="parametros.tipocfdi && tipoCFDIOption.id === parametros.tipocfdi.id ? parametros.tipocfdi : tipoCFDIOption"
                v-for="tipoCFDIOption in tipoCFDIS"
                :key="tipoCFDIOption.id"
              >
                {{ tipoCFDIOption.id }}
              </option>
            </select>
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
            :disabled="$v.parametros.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./parametros-update.component.ts"></script>
