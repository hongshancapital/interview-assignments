//
//  LoadableList.swift
//  AppGallery
//
//  Created by X Tommy on 2022/8/11.
//

import SwiftUI

struct LoadableList<Source: ListObject, Content: View>: View {
    
    var source: Source
    var content: (Source.Output) -> Content
    
    init(source: Source,
         @ViewBuilder content: @escaping (Source.Output) -> Content) {
        self.source = source
        self.content = content
    }
    
    var body: some View {
        
        switch source.loadState {
        case .idle:
            Color.clear.onAppear {
                Task {
                    await source.load()
                }
            }
        case .loading:
            ProgressView()
        case .error(let error):
            Text(error.localizedDescription)
        case .loaded(let items, let hasMore):
            List {
                ForEach(items) { app in
                    content(app)
                        .task {
                            if app == items.last {
                                await source.loadMore()
                            }
                        }
                }
                ListLoadingRow(hasMoreData: hasMore,
                               loading: source.isFetching)
                .frame(maxWidth: .infinity, alignment: .center)
                .listRowSeparator(.hidden)
                .listRowBackground(Color.clear)
            }
            .listStyle(.grouped)
            .refreshable {
                await source.refresh()
            }
        }
    }
    
}

struct LoadableList_Previews: PreviewProvider {
    static var previews: some View {
        LoadableList(source: AppListViewModel(service: RemoteAppListService(), pageSize: 20)) { app in
            Text(app.name)
        }
    }
}
