export interface Data_Storage_Intf {
  save_a_mapping (short_url_id:number, orig_url:string, orig_url_chksum:string): Promise<boolean>,
  remove_a_mapping (short_url_id:number): Promise<boolean>,
  query_short_url_id_with (check_sum:string): Promise<number|undefined>,
  query_original_url_with (short_url_id:number): Promise<string|undefined>
}
