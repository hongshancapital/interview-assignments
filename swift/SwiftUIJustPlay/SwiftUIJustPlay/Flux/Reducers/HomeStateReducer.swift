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
        state = mergeAppItems(appItems: action.response.results.map { AppItemVM(appItem: $0)},
                              state: state)
        break
    case let action as JustPlayActions.RefreshAppStoreList:
        appIdentifier = 0
        let items = action.response.results.map { AppItemVM(appItem: $0)}
        for appItem in items {
            state.orderItems.append(appItem)
        }
        break
    default:
        break
    }
    
    return state
}

private func mergeAppItems(appItems: [AppItemVM], state: HomeState) -> HomeState {
    var state = state
    
    for appItem in appItems {
        state.distinctItems[appItem.id] = appItem
        
    }
    
    state.orderItems = state.distinctItems.sorted { $0.value.orderId < $1.value.orderId }.map { $0.value }
    
    return state
}
