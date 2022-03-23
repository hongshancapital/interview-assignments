export type AnyMap = Record<string, any>;

export interface IPageConfigData {
  parent: any;
  logo: string;
  id: string;
  banner: string;
  name: string;
  updated_time: string;

  icon: string;

  action: string;
  action_type: string;

  data: string;
  
  desc: string;
  sort: number;
}
export interface IfallbackMap {
  [url: string]: IPageConfigData[];
}
 
export interface IfallbackOptions {
  fallback: IfallbackMap;
}

 
export interface IfallbackObjectOptions {
  fallback: {
    [url: string]: IPageConfigData
  };
}