<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="proyecto03App.recibo.home.createOrEditLabel"
          data-cy="ReciboCreateUpdateHeading"
          v-text="$t('proyecto03App.recibo.home.createOrEditLabel')"
        >
          Create or edit a Recibo
        </h2>
        <div>
          <div class="form-group" v-if="recibo.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="recibo.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.recibo.fecha')" for="recibo-fecha">Fecha</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="recibo-fecha"
                  v-model="$v.recibo.fecha.$model"
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
                id="recibo-fecha"
                data-cy="fecha"
                type="text"
                class="form-control"
                name="fecha"
                :class="{ valid: !$v.recibo.fecha.$invalid, invalid: $v.recibo.fecha.$invalid }"
                v-model="$v.recibo.fecha.$model"
                required
              />
            </b-input-group>
            <div v-if="$v.recibo.fecha.$anyDirty && $v.recibo.fecha.$invalid">
              <small class="form-text text-danger" v-if="!$v.recibo.fecha.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.recibo.idInstit')" for="recibo-idInstit">Id Instit</label>
            <input
              type="number"
              class="form-control"
              name="idInstit"
              id="recibo-idInstit"
              data-cy="idInstit"
              :class="{ valid: !$v.recibo.idInstit.$invalid, invalid: $v.recibo.idInstit.$invalid }"
              v-model.number="$v.recibo.idInstit.$model"
              required
            />
            <div v-if="$v.recibo.idInstit.$anyDirty && $v.recibo.idInstit.$invalid">
              <small class="form-text text-danger" v-if="!$v.recibo.idInstit.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.recibo.idInstit.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.recibo.nivel')" for="recibo-nivel">Nivel</label>
            <input
              type="number"
              class="form-control"
              name="nivel"
              id="recibo-nivel"
              data-cy="nivel"
              :class="{ valid: !$v.recibo.nivel.$invalid, invalid: $v.recibo.nivel.$invalid }"
              v-model.number="$v.recibo.nivel.$model"
              required
            />
            <div v-if="$v.recibo.nivel.$anyDirty && $v.recibo.nivel.$invalid">
              <small class="form-text text-danger" v-if="!$v.recibo.nivel.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
              <small class="form-text text-danger" v-if="!$v.recibo.nivel.numeric" v-text="$t('entity.validation.number')">
                This field should be a number.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.recibo.tipoCFDI')" for="recibo-tipoCFDI">Tipo CFDI</label>
            <input
              type="text"
              class="form-control"
              name="tipoCFDI"
              id="recibo-tipoCFDI"
              data-cy="tipoCFDI"
              :class="{ valid: !$v.recibo.tipoCFDI.$invalid, invalid: $v.recibo.tipoCFDI.$invalid }"
              v-model="$v.recibo.tipoCFDI.$model"
              required
            />
            <div v-if="$v.recibo.tipoCFDI.$anyDirty && $v.recibo.tipoCFDI.$invalid">
              <small class="form-text text-danger" v-if="!$v.recibo.tipoCFDI.required" v-text="$t('entity.validation.required')">
                This field is required.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.recibo.clave')" for="recibo-clave">Clave</label>
            <input
              type="text"
              class="form-control"
              name="clave"
              id="recibo-clave"
              data-cy="clave"
              :class="{ valid: !$v.recibo.clave.$invalid, invalid: $v.recibo.clave.$invalid }"
              v-model="$v.recibo.clave.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.recibo.envioConCFDI')" for="recibo-envioConCFDI"
              >Envio Con CFDI</label
            >
            <input
              type="text"
              class="form-control"
              name="envioConCFDI"
              id="recibo-envioConCFDI"
              data-cy="envioConCFDI"
              :class="{ valid: !$v.recibo.envioConCFDI.$invalid, invalid: $v.recibo.envioConCFDI.$invalid }"
              v-model="$v.recibo.envioConCFDI.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.recibo.envioSinCFDI')" for="recibo-envioSinCFDI"
              >Envio Sin CFDI</label
            >
            <input
              type="text"
              class="form-control"
              name="envioSinCFDI"
              id="recibo-envioSinCFDI"
              data-cy="envioSinCFDI"
              :class="{ valid: !$v.recibo.envioSinCFDI.$invalid, invalid: $v.recibo.envioSinCFDI.$invalid }"
              v-model="$v.recibo.envioSinCFDI.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.recibo.aplicaLeyenda')" for="recibo-aplicaLeyenda"
              >Aplica Leyenda</label
            >
            <input
              type="text"
              class="form-control"
              name="aplicaLeyenda"
              id="recibo-aplicaLeyenda"
              data-cy="aplicaLeyenda"
              :class="{ valid: !$v.recibo.aplicaLeyenda.$invalid, invalid: $v.recibo.aplicaLeyenda.$invalid }"
              v-model="$v.recibo.aplicaLeyenda.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.recibo.leyenda')" for="recibo-leyenda">Leyenda</label>
            <input
              type="number"
              class="form-control"
              name="leyenda"
              id="recibo-leyenda"
              data-cy="leyenda"
              :class="{ valid: !$v.recibo.leyenda.$invalid, invalid: $v.recibo.leyenda.$invalid }"
              v-model.number="$v.recibo.leyenda.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.recibo.usuario')" for="recibo-usuario">Usuario</label>
            <input
              type="text"
              class="form-control"
              name="usuario"
              id="recibo-usuario"
              data-cy="usuario"
              :class="{ valid: !$v.recibo.usuario.$invalid, invalid: $v.recibo.usuario.$invalid }"
              v-model="$v.recibo.usuario.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.recibo.fechaMod')" for="recibo-fechaMod">Fecha Mod</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="recibo-fechaMod"
                  v-model="$v.recibo.fechaMod.$model"
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
                id="recibo-fechaMod"
                data-cy="fechaMod"
                type="text"
                class="form-control"
                name="fechaMod"
                :class="{ valid: !$v.recibo.fechaMod.$invalid, invalid: $v.recibo.fechaMod.$invalid }"
                v-model="$v.recibo.fechaMod.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.recibo.tiporecibo')" for="recibo-tiporecibo">Tiporecibo</label>
            <select class="form-control" id="recibo-tiporecibo" data-cy="tiporecibo" name="tiporecibo" v-model="recibo.tiporecibo">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="recibo.tiporecibo && tipoReciboOption.id === recibo.tiporecibo.id ? recibo.tiporecibo : tipoReciboOption"
                v-for="tipoReciboOption in tipoRecibos"
                :key="tipoReciboOption.id"
              >
                {{ tipoReciboOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.recibo.repgrafica')" for="recibo-repgrafica">Repgrafica</label>
            <select class="form-control" id="recibo-repgrafica" data-cy="repgrafica" name="repgrafica" v-model="recibo.repgrafica">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="recibo.repgrafica && repGraficaOption.id === recibo.repgrafica.id ? recibo.repgrafica : repGraficaOption"
                v-for="repGraficaOption in repGraficas"
                :key="repGraficaOption.id"
              >
                {{ repGraficaOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('proyecto03App.recibo.parametros')" for="recibo-parametros">Parametros</label>
            <select class="form-control" id="recibo-parametros" data-cy="parametros" name="parametros" v-model="recibo.parametros">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="recibo.parametros && parametrosOption.id === recibo.parametros.id ? recibo.parametros : parametrosOption"
                v-for="parametrosOption in parametros"
                :key="parametrosOption.id"
              >
                {{ parametrosOption.id }}
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
            :disabled="$v.recibo.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./recibo-update.component.ts"></script>
