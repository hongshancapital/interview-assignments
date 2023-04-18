import { SchemaFor } from '../types/schema';
import { LongDnsDTO, ShortDnsDTO } from '../types/short-dns';

export const LongDnsDTOSchema: SchemaFor<LongDnsDTO> = {
  url: 'string',
  exp: 'number',
};

export const DnsDTOSchema: SchemaFor<ShortDnsDTO> = {
  url: 'string',
};
