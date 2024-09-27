//
//  AppFooterView.swift
//  AppList
//
//  Created by haojiajia on 2022/7/9.
//

import SwiftUI

enum AppFooterViewState {
    case idle
    case pulling
    case willRefresh
    case refreshing
    case noMoreData
    case refreshFailed
}

struct AppFooterView: View {
    
    @EnvironmentObject var store: AppStore
    
    var body: some View {
        HStack(alignment: .center, spacing: 5) {
            Spacer()
            switch store.appState.footerState {
            case .idle, .pulling, .willRefresh:
                Spacer()
            case .refreshing:
                ProgressView()
                    .progressViewStyle(CircularProgressViewStyle(tint: Color.gray))
                Text("Loading...")
            case .noMoreData:
                Text("No More Data.")
            case .refreshFailed:
                Text("Load Failed")
            }
            Spacer()
        }
        .font(.subheadline)
        .foregroundColor(.gray)
    }
    
}

#if DEBUG
struct AppFooterView_Previews: PreviewProvider {
    static var previews: some View {
        AppFooterView().environmentObject(AppStore())
    }
}
#endif
