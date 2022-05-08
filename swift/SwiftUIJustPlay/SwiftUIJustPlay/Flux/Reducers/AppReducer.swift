//
//  AppReducers.swift
//  SwiftUIJustPlay
//
//  Created by wangrenzhu2021 on 2022/5/8.
//

import Foundation
import SwiftUIFlux

func appStateReducer(state: AppState, action: Action) -> AppState {
    var state = state
    state.homeState = homeStateReducer(state: state.homeState, action: action)
    return state
}
