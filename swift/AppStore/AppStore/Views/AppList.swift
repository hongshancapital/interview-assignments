//
//  AppList.swift
//  AppStore
//
//  Created by liuxing on 2022/4/11.
//

import SwiftUI

struct AppList: View {
    @EnvironmentObject var viewModel: AppListViewModel
    
    var body: some View {
        NavigationView {
            Group {
                if viewModel.appModelList.count == 0 {
                    ProgressView()
                } else {
                    ForEach(viewModel.appModelList) { appModel in
                        AppRow(appModel: appModel, tapFavoriteAction: {
                            
                        })
                        .listRowInsets(EdgeInsets())
                        .listRowSeparator(.hidden)
                        .listRowBackground(Color.clear)
                    }
                    
                    LoadMoreView(hasMoreData: viewModel.hasMoreData) {
                        
                    }
                    .listRowBackground(Color.clear)
                }
            }
            .navigationTitle("App")
        }
    }
}

struct AppList_Previews: PreviewProvider {
    static var previews: some View {
        let model1 = AppModel(
            id: 1163852619,
            artworkUrl60: URL(string: "https://is3-ssl.mzstatic.com/image/thumb/Purple112/v4/73/df/36/73df361c-aa5a-7498-540a-0a83e57dfdf0/source/60x60bb.jpg")!,
            trackName: "Viber Messenger: Chats &amp; Calls",
            description: "Viber is the FREE, simple, fast, and secure messaging and calling app.",
            isFavorite: true
        )
        
        let model2 = AppModel(
            id: 1163852620,
            artworkUrl60: URL(string: "https://is3-ssl.mzstatic.com/image/thumb/Purple112/v4/73/df/36/73df361c-aa5a-7498-540a-0a83e57dfdf0/source/60x60bb.jpg")!,
            trackName: "Viber Messenger: Chats &amp; Calls",
            description: "Viber is the FREE, simple, fast, and secure messaging and calling app.",
            isFavorite: true
        )
        
        let model3 = AppModel(
            id: 1163852621,
            artworkUrl60: URL(string: "https://is3-ssl.mzstatic.com/image/thumb/Purple112/v4/73/df/36/73df361c-aa5a-7498-540a-0a83e57dfdf0/source/60x60bb.jpg")!,
            trackName: "Viber Messenger: Chats &amp; Calls",
            description: "Viber is the FREE, simple, fast, and secure messaging and calling app.",
            isFavorite: true
        )
        
        let viewModel = AppListViewModel()
        viewModel.appModelList = [model1, model2, model3]
        return AppList().environmentObject(viewModel)
    }
}
