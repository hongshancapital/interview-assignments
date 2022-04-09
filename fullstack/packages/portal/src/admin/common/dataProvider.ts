import { stringify } from 'qs';
import { fetchUtils, DataProvider, GetManyParams } from 'ra-core';
import { GetListParams, GetOneParams } from 'react-admin';

/**
 * Maps react-admin queries to a simple REST API
 *
 * This REST dialect is similar to the one of FakeRest
 *
 * @see https://github.com/marmelab/FakeRest
 *
 * @example
 *
 * getList     => GET http://my.api.url/posts?sort=['title','ASC']&range=[0, 24]
 * getOne      => GET http://my.api.url/posts/123
 * getMany     => GET http://my.api.url/posts?filter={id:[123,456,789]}
 * update      => PUT http://my.api.url/posts/123
 * create      => POST http://my.api.url/posts
 * delete      => DELETE http://my.api.url/posts/123
 *
 * @example
 *
 * import * as React from "react";
 * import { Admin, Resource } from 'react-admin';
 * import simpleRestProvider from 'ra-data-simple-rest';
 *
 * import { PostList } from './posts';
 *
 * const App = () => (
 *     <Admin dataProvider={simpleRestProvider('http://path.to.my.api/')}>
 *         <Resource name="posts" list={PostList} />
 *     </Admin>
 * );
 *
 * export default App;
 */
const simpleRestProvider = (
    apiUrl: string,
    httpClient = fetchUtils.fetchJson,
): DataProvider => ({
    getList: (resource, params) => {
        const { page, perPage } = params.pagination;
        const { field, order } = params.sort;

        const query = {
            sort: [field, order],
            skip: (page - 1) * perPage,
            take: perPage,
            where: params.filter,
        };
        const url = `${apiUrl}/${resource}?${stringify(query)}`;
        return httpClient(url, {}).then(({ json }) => {
            return {
                ...json,
                data: json.data,
                total: json.page.count
            };
        });
    },

    getOne: (resource, params) => {
      return httpClient(`${apiUrl}/${resource}/${params.id}`).then(({ json }) => ({
        ...json,
          data: json.data,
      }))
    },

    getMany: (resource, params) => {
        const query = {
            where: { id: params.ids },
        };
        const url = `${apiUrl}/${resource}?${stringify(query)}`;
        return httpClient(url).then(({ json }) => ({ ...json, data: json.data }));
    },

    getManyReference: (resource, params) => {
        const { page, perPage } = params.pagination;
        const { field, order } = params.sort;

        const query = {
            sort: [field, order],
            skip: (page - 1) * perPage,
            take: perPage,
            where: {
                ...params.filter,
                [params.target]: params.id,
            },
        };
        const url = `${apiUrl}/${resource}?${stringify(query)}`;

        return httpClient(url, {}).then(({ headers, json }) => {
            return {
              ...json,
                data: json.data,
                total: json.count
            };
        });
    },

    update: (resource, params) =>
        httpClient(`${apiUrl}/${resource}`, {
            method: 'PUT',
            body: JSON.stringify(params.data),
        }).then(({ json }) => ({ ...json, data: json.data })),

    // simple-rest doesn't handle provide an updateMany route, so we fallback to calling update n times instead
    updateMany: (resource, params) =>
        Promise.all(
            params.ids.map(id =>
                httpClient(`${apiUrl}/${resource}/${id}`, {
                    method: 'PUT',
                    body: JSON.stringify(params.data),
                })
            )
        ).then(responses => ({ data: responses.map(({ json }) => json.id) })),

    create: (resource, params) =>
        httpClient(`${apiUrl}/${resource}`, {
            method: 'POST',
            body: JSON.stringify(params.data),
        }).then(({ json }) => ({
            data: { ...params.data, id: json.id },
        })),

    delete: (resource, params) =>
        httpClient(`${apiUrl}/${resource}/${params.id}`, {
            method: 'DELETE',
            headers: new Headers({
                'Content-Type': 'text/plain',
            }),
        }).then(({ json }) => ({ data: json })),

    // simple-rest doesn't handle filters on DELETE route, so we fallback to calling DELETE n times instead
    deleteMany: (resource, params) =>
        Promise.all(
            params.ids.map(id =>
                httpClient(`${apiUrl}/${resource}/${id}`, {
                    method: 'DELETE',
                    headers: new Headers({
                        'Content-Type': 'text/plain',
                    }),
                })
            )
        ).then(responses => ({
            data: responses.map(({ json }) => json.id),
        })),
});


const cacheDataProviderProxy = (dataProvider: DataProvider, duration =  5 * 60 * 1000) => {
  return new Proxy(dataProvider, {
        get: (target: DataProvider, name: string) => (resource: string, params: GetListParams & GetOneParams & GetManyParams) => {
            if (name === 'getOne' || name === 'getMany' || name === 'getList') {
                return dataProvider[name](resource, params).then(response => {
                    const validUntil = new Date();
                    validUntil.setTime(validUntil.getTime() + duration);
                    response.validUntil = validUntil;
                    return response;
                });
            }
            return dataProvider[name](resource, params);
        },
    });
}

const dataProvider = simpleRestProvider('http://localhost:3332/api');
export default cacheDataProviderProxy(dataProvider);