//
//  HomeList.swift
//  App
//
//  Created by august on 2022/3/21.
//

import SwiftUI

struct HomeList: View {
    
    @StateObject var listModel = AppSearchListModel()
    
    var body: some View {
        NavigationView {
            Group {
                if listModel.apps.count > 0 {
                    List {
                        //list cell
                        ForEach(listModel.apps, id: \.trackId) { app in
                            HomeListCell(information: app)
                                .listRowInsets(.init(top: 8, leading: 0, bottom: 8, trailing: 0))
                                .listRowSeparator(.hidden)
                                .listRowBackground(Color.clear)
                        }
                        // load more view
                        LoadMoreView(hasMoreData: $listModel.hasMoreData)
                            .listRowBackground(Color.clear)
                            .task {
                                //delay 1s to seen load more view
                                try? await Task.sleep(nanoseconds: 1_000_000_000)
                                try? await listModel.loadMore()
                            }
                    }
                    .refreshable {
                        try? await listModel.refresh()
                    }
                } else {
                    ProgressView()
                        .frame(alignment: .center)
                }
            }
            .navigationTitle("App")
        }
        .task {
            try? await listModel.refresh()
        }
    }
}

struct HomeList_Previews: PreviewProvider {
    static var previews: some View {
        HomeList()
    }
}
