import * as Ripe_MD160 from 'ripemd160';
import {
  SHORT_URL_TOKEN_LENGTH, CHECK_SUM_LENGTH, BASE64CHARSET, ENCODE_BASE, FALSY_ID, FALSY_RANGE_ID,
} from '../constants';

function shuffle_string(s:string):string {
  if (s.length === 0) { return ''; }

  const r = Array.from(s);
  for (let i = 0; i < Math.floor(r.length / 2); i += 2) {
    const c = r[i];
    r[i] = r[r.length - 1 - i];
    r[r.length - 1 - i] = c;
  }

  return r.join('');
}

function random_int(low:number, high:number) : number {
  return Math.floor(Math.random() * (high - low) + low);
}

export function is_valid_short_url(url:string|undefined, base_url:string) :boolean {
  if ((url === undefined) || !url.startsWith(base_url)) { return false; }
  const s = url.substring(base_url.length);
  const pattern = `[A-Za-z0-9+=]{${SHORT_URL_TOKEN_LENGTH}}`;
  return (s.length === SHORT_URL_TOKEN_LENGTH) ? new RegExp(pattern).test(url) : false;
}

export function calculate_check_sum(s:string) : string {
  return new Ripe_MD160().update(s).digest('hex');
}

export function make_range_id_from(check_sum:string, range_id_len:number) : number {
  if ((check_sum.length !== CHECK_SUM_LENGTH)
      || (range_id_len < 1) || (check_sum.length < range_id_len * 2)) {
    return FALSY_RANGE_ID;
  }

  const pattern = `[0-9abcdef]{${CHECK_SUM_LENGTH}}`;
  if (new RegExp(pattern).test(check_sum) === false) { return FALSY_RANGE_ID; }

  const s = `${check_sum}${check_sum}`;
  const start = random_int(0, check_sum.length);
  return parseInt(s.substr(start, range_id_len * 2), 16);
}

export function build_short_url_from(unique_id:number, base_url:string):string {
  let s = '';
  let k = unique_id;
  for (let i = 0; i < SHORT_URL_TOKEN_LENGTH; i += 1) {
    const m:number = k % ENCODE_BASE;
    s = BASE64CHARSET[m] + s;
    k = (k - m) / ENCODE_BASE;
  }
  return base_url + shuffle_string(s);
}

export function build_short_url_id_from(short_url:string, base_url:string) : number {
  if (!is_valid_short_url(short_url, base_url)) { return FALSY_ID; }

  const s = shuffle_string(short_url.substring(base_url.length));

  let n = 0;
  for (let i = 0; i < SHORT_URL_TOKEN_LENGTH; i += 1) {
    n = n * ENCODE_BASE + BASE64CHARSET.findIndex((x) => (x === s.charAt(i)));
  }
  return n;
}
