import Vue from 'vue';
import Vuex, { StoreOptions } from 'vuex';
import { RootState } from './stateModel';
import user from './user';

Vue.use(Vuex);
const state: RootState = {
    loginStatus: false,
    language: window.navigator.language.toLowerCase() as SupportLanguageType,
    menuHidden: false,
    screenType: 'pc'
};

const store: StoreOptions<RootState> = {
    modules: {
        user
    },
    state,
    mutations: {
        _toogleSideShrink(state: RootState) {
            state.menuHidden = !state.menuHidden;
        },
        _login(state: RootState) {
            state.loginStatus = true;
        },
        _logout(state: RootState) {
            state.loginStatus = false;
        },
        _setLanguage(state: RootState, language: SupportLanguageType) {
            if (state.language !== language) {
                state.language = language;
            }
        },
        _setScreenType(state: RootState, type: 'phone' | 'ipad' | 'spc' | 'pc') {
            if (state.screenType !== type) {
                state.screenType = type;

                if (state.screenType === 'phone' || state.screenType === 'ipad') {
                    state.menuHidden = true;
                }
            }
        }
    },
    actions: {
        toogleSideShrink({ commit }) {
            commit('_toogleSideShrink');
        },
        login({ commit }) {
            commit('_login');
        },
        logout({ commit }) {
            commit('_logout');
        },
        setLanguage({ commit }, language: SupportLanguageType) {
            commit('_setLanguage', language);
        },
        setScreenType({ commit }) {
            const size = document.body.offsetWidth;

            if (size <= 767) {
                commit('_setScreenType', 'phone');
            } else if (size > 1200) {
                commit('_setScreenType', 'pc');
            } else if (size > 992) {
                commit('_setScreenType', 'spc');
            } else if (size > 768) {
                commit('_setScreenType', 'ipad');
            }
        }
    }
};

export default new Vuex.Store<RootState>(store);
