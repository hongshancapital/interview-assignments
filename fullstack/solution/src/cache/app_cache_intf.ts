export interface App_Cache_Intf {
  save_check_sum_and_short_url_id : (a:string, b:number) => void,
  save_short_url_id_and_original_url : (a:number, b:string) => void,
  retrieve_short_url_id_with : (s:string) => number|undefined,
  retrieve_original_url_with : (n:number) => string|undefined
}
