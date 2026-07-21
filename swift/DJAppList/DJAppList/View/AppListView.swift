//
//  AppListView.swift
//  AppList
//
//  Created by haojiajia on 2022/7/7.
//

import Foundation
import SwiftUI
import Combine

struct AppListView: View {
    
    @EnvironmentObject var store: AppStore
    private var list: [AppItem] {
        store.appState.apps
    }
    
    var body: some View {
        List {
            ForEach(list) { item in
                AppItemView(item: item)
            }
            .listRowBackground(EmptyView())
            .listRowSeparator(.hidden)
            .textSelection(.disabled)
            
            AppFooterView()
                .listRowBackground(EmptyView())
                .offset(x: 0, y: -10)
                .frame(height: 60, alignment: .center)
                .onAppear {
                    self.store.dispatch(.loadMoreApps)
                }
        }
        .background(Color(UIColor.secondarySystemBackground))
        .listStyle(.plain)
    }
}

#if DEBUG
struct AppListView_Preview: PreviewProvider {
    
    static var previews: some View {
        ForEach(["iPhone SE", "iPhone XS Max", "iPad mini 2"], id: \.self) { deviceName in
            AppListView()
                .environmentObject(AppStore.sample)
                .previewDevice(PreviewDevice(rawValue: deviceName))
                .previewDisplayName(deviceName)
        }
    }
}
#endif
