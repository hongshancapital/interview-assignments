//
//  HomeStateReducer.swift
//  SwiftUIJustPlay
//
//  Created by wangrenzhu2021 on 2022/5/8.
//

import Foundation
import SwiftUIFlux
import UIKit

func homeStateReducer(state: HomeState, action: Action) -> HomeState {
    var state = state
    switch action {
    case let action as JustPlayActions.SetAppStoreList:
        state.appItems += action.response.results.map { AppItemVM(appItem: $0)}

    default:
        break
    }
    
    
    return state
}

func +=(lhs: inout [Int : AppItemVM], rhs: [AppItemVM]) {
    for appItem in rhs {
        lhs[appItem.id] = appItem
    }
}

private func mergeAppItems(appItems: [AppItemVM], state: HomeState) -> HomeState {
    var state = state
    for appItem in appItems {
        if state.appItems[appItem.id] == nil {
            state.appItems[appItem.id] = appItem
        }
    }
    return state
}
