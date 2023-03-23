import axios from 'axios';

import buildPaginationQueryOpts from '@/shared/sort/sorts';

import { IEstatusCFDI } from '@/shared/model/estatus-cfdi.model';

const baseApiUrl = 'api/estatus-cfdis';

export default class EstatusCFDIService {
  public find(id: number): Promise<IEstatusCFDI> {
    return new Promise<IEstatusCFDI>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/${id}`)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public retrieve(paginationQuery?: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(baseApiUrl + `?${buildPaginationQueryOpts(paginationQuery)}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public delete(id: number): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .delete(`${baseApiUrl}/${id}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public create(entity: IEstatusCFDI): Promise<IEstatusCFDI> {
    return new Promise<IEstatusCFDI>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}`, entity)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public update(entity: IEstatusCFDI): Promise<IEstatusCFDI> {
    return new Promise<IEstatusCFDI>((resolve, reject) => {
      axios
        .put(`${baseApiUrl}/${entity.id}`, entity)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public partialUpdate(entity: IEstatusCFDI): Promise<IEstatusCFDI> {
    return new Promise<IEstatusCFDI>((resolve, reject) => {
      axios
        .patch(`${baseApiUrl}/${entity.id}`, entity)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
