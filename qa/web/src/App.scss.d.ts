declare namespace AppScssNamespace {
  export interface IAppScss {
    appContainer: string;
    errorString: string;
    form: string;
    input: string;
    item: string;
    listContainer: string;
  }
}

declare const AppScssModule: AppScssNamespace.IAppScss;

export = AppScssModule;
