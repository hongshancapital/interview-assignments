export interface ID_Service_Repository_Intf {
  create_one_with_range: (range_id: number) => Promise<number>
  create_one_roughly: () => Promise<number>
}
