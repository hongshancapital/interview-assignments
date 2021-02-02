import { Container } from 'inversify';
import 'reflect-metadata';
import Interfaces from '../interfaces';

import { DB_Provider_Intf } from '../id_service/db_provider_intf';
import { ID_Service_Repository_Intf } from '../id_service/id_service_repository_intf';
import { ID_Service_Intf } from '../id_service/id_service_intf';
import { App_Cache_Intf } from '../cache/app_cache_intf';
import { Data_Storage_Intf } from '../data_storage/data_storage_intf';

import DB_Provider_MySQL from '../id_service/db_provider_mysql';
import ID_Service_Repository_Impl from '../id_service/id_service_repository_impl';
import ID_Service_Impl from '../id_service/id_service_impl';
import App_Cache from '../cache/app_cache';
import Data_Storage from '../data_storage/data_storage';

import Controller from '../controller/controller';
import App from './app';

const container = new Container();
container.bind<DB_Provider_Intf>(Interfaces.DB_Provider).to(DB_Provider_MySQL).inSingletonScope();
container.bind<ID_Service_Repository_Intf>(Interfaces.ID_Service_Repository)
  .to(ID_Service_Repository_Impl).inSingletonScope();
container.bind<ID_Service_Intf>(Interfaces.ID_Service).to(ID_Service_Impl).inSingletonScope();
container.bind<App_Cache_Intf>(Interfaces.Cache).to(App_Cache).inSingletonScope();
container.bind<Data_Storage_Intf>(Interfaces.Storage).to(Data_Storage).inSingletonScope();
container.bind<Controller>(Controller).to(Controller).inSingletonScope();
container.bind<App>(App).to(App).inSingletonScope();

export default container;
