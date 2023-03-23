<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="proyecto03App.notificaciones.home.createOrEditLabel"
          data-cy="NotificacionesCreateUpdateHeading"
          v-text="$t('proyecto03App.notificaciones.home.createOrEditLabel')"
        >
          Create or edit a Notificaciones
        </h2>
        <div>
          <div class="form-group" v-if="notificaciones.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="notificaciones.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.notificaciones.fecha')" for="notificaciones-fecha">Fecha</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="notificaciones-fecha"
                  v-model="$v.notificaciones.fecha.$model"
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
                id="notificaciones-fecha"
                data-cy="fecha"
                type="text"
                class="form-control"
                name="fecha"
                :class="{ valid: !$v.notificaciones.fecha.$invalid, invalid: $v.notificaciones.fecha.$invalid }"
                v-model="$v.notificaciones.fecha.$model"
                required
              />
            </b-input-group>
            <div v-if="$v.notificaciones.fecha.$anyDirty && $v.notificaciones.fecha.$invalid">
              <small class="form-text text-danger" v-if="!$v.notificaciones.fecha.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.notificaciones.idInstit')" for="notificaciones-idInstit"
              >Id Instit</label
            >
            <input
              type="number"
              class="form-control"
              name="idInstit"
              id="notificaciones-idInstit"
              data-cy="idInstit"
              :class="{ valid: !$v.notificaciones.idInstit.$invalid, invalid: $v.notificaciones.idInstit.$invalid }"
              v-model.number="$v.notificaciones.idInstit.$model"
              required
            />
            <div v-if="$v.notificaciones.idInstit.$anyDirty && $v.notificaciones.idInstit.$invalid">
              <small class="form-text text-danger" v-if="!$v.notificaciones.idInstit.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.notificaciones.idInstit.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.notificaciones.nivel')" for="notificaciones-nivel">Nivel</label>
            <input
              type="number"
              class="form-control"
              name="nivel"
              id="notificaciones-nivel"
              data-cy="nivel"
              :class="{ valid: !$v.notificaciones.nivel.$invalid, invalid: $v.notificaciones.nivel.$invalid }"
              v-model.number="$v.notificaciones.nivel.$model"
              required
            />
            <div v-if="$v.notificaciones.nivel.$anyDirty && $v.notificaciones.nivel.$invalid">
              <small class="form-text text-danger" v-if="!$v.notificaciones.nivel.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.notificaciones.nivel.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.notificaciones.tipoNot')" for="notificaciones-tipoNot"
              >Tipo Not</label
            >
            <select
              class="form-control"
              name="tipoNot"
              :class="{ valid: !$v.notificaciones.tipoNot.$invalid, invalid: $v.notificaciones.tipoNot.$invalid }"
              v-model="$v.notificaciones.tipoNot.$model"
              id="notificaciones-tipoNot"
              data-cy="tipoNot"
            >
              <option
                v-for="tipoNot in tipoNotValues"
                :key="tipoNot"
                v-bind:value="tipoNot"
                v-bind:label="$t('proyecto03App.TipoNot.' + tipoNot)"
              >
                {{ tipoNot }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.notificaciones.clave')" for="notificaciones-clave">Clave</label>
            <input
              type="text"
              class="form-control"
              name="clave"
              id="notificaciones-clave"
              data-cy="clave"
              :class="{ valid: !$v.notificaciones.clave.$invalid, invalid: $v.notificaciones.clave.$invalid }"
              v-model="$v.notificaciones.clave.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.notificaciones.asunto')" for="notificaciones-asunto">Asunto</label>
            <input
              type="text"
              class="form-control"
              name="asunto"
              id="notificaciones-asunto"
              data-cy="asunto"
              :class="{ valid: !$v.notificaciones.asunto.$invalid, invalid: $v.notificaciones.asunto.$invalid }"
              v-model="$v.notificaciones.asunto.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.notificaciones.texto')" for="notificaciones-texto">Texto</label>
            <input
              type="number"
              class="form-control"
              name="texto"
              id="notificaciones-texto"
              data-cy="texto"
              :class="{ valid: !$v.notificaciones.texto.$invalid, invalid: $v.notificaciones.texto.$invalid }"
              v-model.number="$v.notificaciones.texto.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.notificaciones.piePagina')" for="notificaciones-piePagina"
              >Pie Pagina</label
            >
            <input
              type="text"
              class="form-control"
              name="piePagina"
              id="notificaciones-piePagina"
              data-cy="piePagina"
              :class="{ valid: !$v.notificaciones.piePagina.$invalid, invalid: $v.notificaciones.piePagina.$invalid }"
              v-model="$v.notificaciones.piePagina.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.notificaciones.usuario')" for="notificaciones-usuario"
              >Usuario</label
            >
            <input
              type="text"
              class="form-control"
              name="usuario"
              id="notificaciones-usuario"
              data-cy="usuario"
              :class="{ valid: !$v.notificaciones.usuario.$invalid, invalid: $v.notificaciones.usuario.$invalid }"
              v-model="$v.notificaciones.usuario.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.notificaciones.fechaMod')" for="notificaciones-fechaMod"
              >Fecha Mod</label
            >
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="notificaciones-fechaMod"
                  v-model="$v.notificaciones.fechaMod.$model"
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
                id="notificaciones-fechaMod"
                data-cy="fechaMod"
                type="text"
                class="form-control"
                name="fechaMod"
                :class="{ valid: !$v.notificaciones.fechaMod.$invalid, invalid: $v.notificaciones.fechaMod.$invalid }"
                v-model="$v.notificaciones.fechaMod.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.notificaciones.registinstit')" for="notificaciones-registinstit"
              >Registinstit</label
            >
            <select
              class="form-control"
              id="notificaciones-registinstit"
              data-cy="registinstit"
              name="registinstit"
              v-model="notificaciones.registinstit"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  notificaciones.registinstit && registInstitOption.id === notificaciones.registinstit.id
                    ? notificaciones.registinstit
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
            <label class="form-control-label" v-text="$t('proyecto03App.notificaciones.tipocfdi')" for="notificaciones-tipocfdi"
              >Tipocfdi</label
            >
            <select class="form-control" id="notificaciones-tipocfdi" data-cy="tipocfdi" name="tipocfdi" v-model="notificaciones.tipocfdi">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  notificaciones.tipocfdi && tipoCFDIOption.id === notificaciones.tipocfdi.id ? notificaciones.tipocfdi : tipoCFDIOption
                "
                v-for="tipoCFDIOption in tipoCFDIS"
                :key="tipoCFDIOption.id"
              >
                {{ tipoCFDIOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.notificaciones.tiporecibo')" for="notificaciones-tiporecibo"
              >Tiporecibo</label
            >
            <select
              class="form-control"
              id="notificaciones-tiporecibo"
              data-cy="tiporecibo"
              name="tiporecibo"
              v-model="notificaciones.tiporecibo"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  notificaciones.tiporecibo && tipoReciboOption.id === notificaciones.tiporecibo.id
                    ? notificaciones.tiporecibo
                    : tipoReciboOption
                "
                v-for="tipoReciboOption in tipoRecibos"
                :key="tipoReciboOption.id"
              >
                {{ tipoReciboOption.id }}
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
            :disabled="$v.notificaciones.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./notificaciones-update.component.ts"></script>
