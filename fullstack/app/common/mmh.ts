import { x86 } from 'murmurhash3js';

export default (str: string) : number => {
  if (!str) 
    return 0;

  return x86.hash32(str, 0);
}