//
//  AppListView.swift
//  scdt_interview (iOS)
//
//  Created by Ray Tao on 2022/7/14.
//

import SwiftUI

struct AppListView: View {
    @EnvironmentObject var store: Store

    var list: AppState.AppList { store.appState.appList }

    var listState: ListState { list.listState }

    var body: some View {
        ScrollView {
            PullToRefreshView(header: RefreshDefaultHeader(), footer: RefreshDefaultFooter()) {
                ScrollView {
                    ForEach(list.displayAppList(with: store.appState.user)) { app in
                        AppInfoRow(
                            model: app
                        )
                    }
                    
                }
            }
            .environmentObject(listState)
            
            Spacer().frame(height: 60)
        }
        .addPullToRefresh(isHeaderRefreshing: $store.appState.appList.headerRefresh,
                          onHeaderRefresh: {
                              store.dispatch(.loadAppListHeader)
                          },
                          isFooterRefreshing: $store.appState.appList.footerRefresh,
                          onFooterRefresh: {
                              store.dispatch(.loadAppListFooter(index: list.pageIndex + 1))
                          })
        .navigationBarTitle("App")
    }
}

struct AppListView_Previews: PreviewProvider {
    static var previews: some View {
        AppListView()
    }
}
