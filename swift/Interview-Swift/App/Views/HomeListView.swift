//
//  SearchViewList.swift
//  ProduceImageType
//
//  Created by lizhao on 2022/9/16.
//

import SwiftUI
import GUIKit

struct HomeListView: View {
    @EnvironmentObject var store: Store
    var applist: AppState.AppList {
        store.appState.appList
    }
    var body: some View {
        NavigationView {
            VStack {
                List {
                    ForEach(applist.apps, id: \.app.trackId) { item in
                        HomeListAppRow(model: item)
                    }
                    LoadMoreView()
                }
                .onAppear {
                    self.store.dispatch(.refresh)
                }
                .refreshable {
                    self.store.dispatch(.refresh)
                    try? await Task.sleep(nanoseconds: 500000000) // TODO: 
                }
                .navigationTitle("App")
            }
        }
    }
}

struct SearchViewList_Previews: PreviewProvider {
    static var previews: some View {
        HomeListView().environmentObject(Store())
    }
}
