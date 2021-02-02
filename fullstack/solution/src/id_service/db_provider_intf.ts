export interface DB_Provider_Intf {
  fetch_sequence_of_a_range (range_id:number):Promise<number>
  increase_sequence_of_a_range (range_id:number, sequence:number):Promise<boolean>
  select_a_capable_range_randomly ():Promise<number>
  select_a_range_and_increase_its_sequence_transactionally ():Promise<number[]>
  stop():void
}
