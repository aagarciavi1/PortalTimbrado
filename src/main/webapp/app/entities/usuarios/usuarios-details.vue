<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="usuarios">
        <h2 class="jh-entity-heading" data-cy="usuariosDetailsHeading">
          <span v-text="$t('proyecto03App.usuarios.detail.title')">Usuarios</span> {{ usuarios.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('proyecto03App.usuarios.fecha')">Fecha</span>
          </dt>
          <dd>
            <span>{{ usuarios.fecha }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.usuarios.usuario')">Usuario</span>
          </dt>
          <dd>
            <span>{{ usuarios.usuario }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.usuarios.descr')">Descr</span>
          </dt>
          <dd>
            <span>{{ usuarios.descr }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.usuarios.idInstit')">Id Instit</span>
          </dt>
          <dd>
            <span>{{ usuarios.idInstit }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.usuarios.correo')">Correo</span>
          </dt>
          <dd>
            <span>{{ usuarios.correo }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.usuarios.contrasena')">Contrasena</span>
          </dt>
          <dd>
            <span>{{ usuarios.contrasena }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.usuarios.confirmarContras')">Confirmar Contras</span>
          </dt>
          <dd>
            <span>{{ usuarios.confirmarContras }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.usuarios.estatus')">Estatus</span>
          </dt>
          <dd>
            <span v-text="$t('proyecto03App.Estatus.' + usuarios.estatus)">{{ usuarios.estatus }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.usuarios.usuarioCrea')">Usuario Crea</span>
          </dt>
          <dd>
            <span>{{ usuarios.usuarioCrea }}</span>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.usuarios.registinstit')">Registinstit</span>
          </dt>
          <dd>
            <div v-if="usuarios.registinstit">
              <router-link :to="{ name: 'RegistInstitView', params: { registInstitId: usuarios.registinstit.id } }">{{
                usuarios.registinstit.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.usuarios.perfil')">Perfil</span>
          </dt>
          <dd>
            <div v-if="usuarios.perfil">
              <router-link :to="{ name: 'PerfilesView', params: { perfilesId: usuarios.perfil.id } }">{{ usuarios.perfil.id }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.usuarios.tipocfdi')">Tipocfdi</span>
          </dt>
          <dd>
            <div v-if="usuarios.tipocfdi">
              <router-link :to="{ name: 'TipoCFDIView', params: { tipoCFDIId: usuarios.tipocfdi.id } }">{{
                usuarios.tipocfdi.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('proyecto03App.usuarios.tiporecibo')">Tiporecibo</span>
          </dt>
          <dd>
            <div v-if="usuarios.tiporecibo">
              <router-link :to="{ name: 'TipoReciboView', params: { tipoReciboId: usuarios.tiporecibo.id } }">{{
                usuarios.tiporecibo.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="usuarios.id" :to="{ name: 'UsuariosEdit', params: { usuariosId: usuarios.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./usuarios-details.component.ts"></script>
