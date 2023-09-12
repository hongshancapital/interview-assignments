import { LongDnsDTO, ShortDnsDTO } from '../types/short-dns';
import { genShortDns, getLongUrl } from './dns-service';
import { SHORT_DNS_DOMAIN } from '../config/env';

export const getShortDNS = async (data: LongDnsDTO, query: {}, context: any): Promise<ShortDnsDTO> => {
  return await genShortDns(data, `${SHORT_DNS_DOMAIN}${context.request.baseUrl}/`);
};

export const getLongDNS = async (data: {}, query: {}, context: any): Promise<ShortDnsDTO> => {
  return await getLongUrl(context.request.params.shortDns);
};
