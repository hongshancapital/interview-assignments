export interface RootState {
    loginStatus: boolean;
    language: SupportLanguageType;
    menuHidden: boolean;
    screenType: 'phone' | 'ipad' | 'spc' | 'pc';
}

export interface UserState {
    username: string;
}

export interface ToogleSideAction {
    (): never;
}

export interface SetLanguageAction {
    // eslint-disable-next-line no-unused-vars
    (language: SupportLanguageType): never;
}
