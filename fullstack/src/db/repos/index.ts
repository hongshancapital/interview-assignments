import {DomainsRepository} from './domains';

// Database Interface Extensions:
interface IExtensions {
    domains: DomainsRepository,
    users:any[]
}

export type {
    IExtensions
};

export {DomainsRepository};
