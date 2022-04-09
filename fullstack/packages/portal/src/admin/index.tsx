import * as React from 'react';
import { Admin, Resource } from 'react-admin';

import dataProvider from './common/dataProvider'

import { ShorturlView, ShorturlCreate, ShorturlEdit, ShorturlList } from './components/shorturl';

const App = () => (
  <Admin dataProvider={dataProvider}>
    <Resource name="shorturls" show={ShorturlView} list={ShorturlList} edit={ShorturlEdit} create={ShorturlCreate} />
  </Admin>
);
export default App;

