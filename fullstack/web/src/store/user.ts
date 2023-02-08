import { MutationTree, ActionTree, Module } from 'vuex';
import { RootState, UserState } from './stateModel';

const state: UserState = {
    username: 'coco'
};
const mutations: MutationTree<UserState> = {
    _setUserName(state: UserState, _username: string) {
        state.username = _username;
    }
};

const actions: ActionTree<UserState, RootState> = {
    setUserName({ commit /*, state*/ }, username: string) {
        commit('_setUserName', username);
    }
};

const user: Module<UserState, RootState> = {
    namespaced: true,
    state,
    mutations,
    actions
};

export default user;
