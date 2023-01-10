import { container } from 'tsyringe';

import IUrlRepository from './IUrlRepository';
import UrlRepository from './UrlRepository';

container.registerSingleton<IUrlRepository>('UrlRepository', UrlRepository);
