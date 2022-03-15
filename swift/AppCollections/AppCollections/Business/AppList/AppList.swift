//
//  AppList.swift
//  AppCollections
//
//  Created by Guang Lei on 2022/3/11.
//

import SwiftUI

struct AppList: View {
    
    @StateObject var viewModel = AppListViewModel()
    
    var body: some View {
        NavigationView {
            Group {
                if viewModel.isFirstLoading {
                    ProgressView().offset(x: 0, y: -70)
                } else {
                    List {
                        ForEach(viewModel.list) { app in
                            AppCell(app: app, tapFavor: { app in
                                viewModel.favor(app)
                            })
                                .listRowSeparator(.hidden)
                                .listRowInsets(EdgeInsets(top: 8, leading: 0, bottom: 8, trailing: 0))
                                .listRowBackground(Color.clear)
                        }
                        if viewModel.list.count > 0 {
                            LoadMoreView(noMoreData: viewModel.noMoreData) {
                                await viewModel.loadMore()
                            }
                            .listRowBackground(Color.clear)
                        }
                    }
                    .refreshable { await viewModel.refresh() }
                }
            }
            .navigationTitle("App")
        }
        .navigationViewStyle(.stack)
        .alert(viewModel.alertMessage, isPresented: $viewModel.showError, actions: {})
        .task { await viewModel.firstLoad() }
    }
}

struct AppList_Previews: PreviewProvider {
    static var previews: some View {
        let app1 = AppModel(
            trackId: 1163852619,
            trackName: "Google Chat",
            description: "Google Chat is an intelligent tool.",
            artworkUrl60: URL(string: "https://is4-ssl.mzstatic.com/image/thumb/Purple116/v4/b1/87/9a/b1879a2c-6790-a031-cc66-f644bd3dc76c/source/60x60bb.jpg")!,
            isFavorite: false
        )
        let app2 = AppModel(
            trackId: 382617920,
            trackName: "Viber Messenger: Chats & Calls",
            description: "Viber is the FREE, simple, secure messaging and calling app.",
            artworkUrl60: URL(string: "https://is2-ssl.mzstatic.com/image/thumb/Purple116/v4/06/8c/e5/068ce5a0-8a33-41ee-488a-95067d2b241a/source/60x60bb.jpg")!,
            isFavorite: true
        )
        let app3 = AppModel(
            trackId: 414478124,
            trackName: "WeChat",
            description: "WeChat is more than a messaging and social media app – it is a lifestyle for one billion users across the world.",
            artworkUrl60: URL(string: "https://is2-ssl.mzstatic.com/image/thumb/Purple126/v4/91/6f/8a/916f8a02-6467-51a7-516e-bad1a86203bc/source/60x60bb.jpg")!,
            isFavorite: false
        )
        let list = [app1, app2, app3]
        let viewModel = AppListViewModel()
        viewModel.isFirstLoading = false
        viewModel.list = list
        return AppList(viewModel: viewModel)
    }
}
