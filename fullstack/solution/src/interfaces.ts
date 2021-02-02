const Interfaces = {
  DB_Provider: Symbol.for('DB_Provider_Intf'),
  ID_Service_Repository: Symbol.for('ID_Service_Repository_Intf'),
  ID_Service: Symbol.for('ID_Service_Intf'),
  Storage: Symbol.for('Data_Storage_Intf'),
  Cache: Symbol.for('App_Cache_Intf'),
  Controller: Symbol.for('Controller_Intf'),
};

export default Interfaces;
