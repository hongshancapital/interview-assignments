import { Container } from 'inversify';
import 'reflect-metadata';
import Interfaces from '../interfaces';

import { ID_Service_Intf } from '../id_service/id_service_intf';
import { App_Cache_Intf } from '../cache/app_cache_intf';
import { Data_Storage_Intf } from '../data_storage/data_storage_intf';

import ID_Service_Simple from './id_service_simple';
import Data_Storage from '../data_storage/__mocks__/data_storage';
import App_Cache from '../cache/app_cache';

import Controller from '../controller/controller';
import App from '../server/app';

const container = new Container();
container.bind<ID_Service_Intf>(Interfaces.ID_Service).to(ID_Service_Simple).inSingletonScope();
container.bind<App_Cache_Intf>(Interfaces.Cache).to(App_Cache).inSingletonScope();
container.bind<Data_Storage_Intf>(Interfaces.Storage).to(Data_Storage).inSingletonScope();
container.bind<Controller>(Controller).to(Controller).inSingletonScope();
container.bind<App>(App).to(App).inSingletonScope();

export default container;
