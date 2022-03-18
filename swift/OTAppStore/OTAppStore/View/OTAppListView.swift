//
//  OTAppListView.swift
//  OTAppStore
//
//  Created by liuxj on 2022/3/18.
//

import SwiftUI

struct OTAppListView: View {
    
    static let appModel1 = AppModel(
        trackId: 1163852619,
        artworkUrl60: "https://is4-ssl.mzstatic.com/image/thumb/Purple116/v4/b1/87/9a/b1879a2c-6790-a031-cc66-f644bd3dc76c/source/60x60bb.jpg",
        trackName: "Google Chat",
        description: "Google Chat is an intelligent ")

//    static let appModel2 =
//    AppModel(
//        trackId: 382617920,
//        artworkUrl60: "https://is2-ssl.mzstatic.com/image/thumb/Purple126/v4/91/6f/8a/916f8a02-6467-51a7-516e-bad1a86203bc/source/60x60bb.jpg",
//        trackName: "Viber Messenger: Chats & Calls",
//        description: "Viber is the FREE, simple, fast, and secure messaging and calling app. It’s the messenger of choice for hundreds of millions of users worldwide!")
    
//     var appModels = [appModel1]
    
    @StateObject var viewModel = OTAppViewModel()
    
    var body: some View {
        NavigationView {
            Group {
                List (viewModel.appModelList, id: \.self) {appModel in
                    OTAppRow(appModel: appModel)
                        .listRowSeparator(.hidden)
                        .listRowInsets(EdgeInsets())
                        .listRowBackground(Color.clear)
                }
            }
            .onAppear {
                if viewModel.appModelList.count == 0 {
                    viewModel.refresh()
                }
            }
            .refreshable {
                viewModel.refresh()
            }
            .navigationTitle("App")
            .toolbar {
                Button("add") {
                    viewModel.loadMoreData()
                }
            }
        }
        .navigationViewStyle(.stack)
        
    }
    
}

struct OTAppListView_Previews: PreviewProvider {
    static var previews: some View {
        OTAppListView()
    }
}
