//
//  EmptyDataModifier.swift
//  AppsDemo
//
//  Created by changcun on 2022/3/11.
//

import SwiftUI

enum EmptyDataState {
    case loading
    case error
    case empty
    case items
}

struct EmptyDataModifier<T: View>: ViewModifier {
    @Binding var state: EmptyDataState
    let placeholder: T

    @ViewBuilder
    func body(content: Content) -> some View {
        if state == .items {
            content
        } else if state == .loading {
            ActivityIndicator(style: .medium)
        } else {
            placeholder
        }
    }
}

extension View {
    /// 当View数据为空时展示一个 placeholder
    /// 提供一个默认参数 placeholder，可自定义
    func emptyPlaceholder(_ state: Binding<EmptyDataState>,
                          _ placeholder: AnyView = AnyView(EmptyDataPlaceholder())) -> some View {
        modifier(EmptyDataModifier(state: state, placeholder: placeholder))
    }
}
