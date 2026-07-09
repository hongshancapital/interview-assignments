//
//  AppListView.swift
//  ListDemo
//
//  Created by 王明友 on 2023/6/10.
//

import Combine
import SwiftUI

struct AppListView: View {
    @EnvironmentObject var store: AppStore

    var appList: AppState.AppList { store.appState.appList }
    
    var body: some View {
        List {
            ForEach(appList.display()) {
                AppCell(viewModel: $0)
                    .listRowBackground(EmptyView())
                    .listRowSeparator(.hidden)
            }
            HStack {
                Spacer()
                if self.appList.hasMore {
                    ProgressView()
                        .padding(.trailing, 5)
                }
                Text(self.appList.hasMore ? "Loading..." : "No more data")
                    .foregroundColor(Color(uiColor: .placeholderText))
                    .onAppear(perform: loadMore)
                Spacer()
            }
            .listRowBackground(EmptyView())
            .listRowSeparator(.hidden)
        }
        .refreshable {
            self.store.dispatch(.refresh)
        }
    }
    
    func loadMore() {
        self.store.dispatch(.loadMore)
    }
}
