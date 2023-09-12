
//
//  ContentView.swift
//  swiftUI
//
//  Created by pchen on 2023/4/3.
//

import SwiftUI
import Combine

struct AppContentView: View {
    
    @StateObject  var appVM = AppViewModel()

    var body: some View {
        
        ZStack {
            NavigationView {
                listView()
            }
            .background(Color(.secondarySystemBackground))
            .task {
                await appVM.refreshApp()
            }
            if appVM.pageState == .isInitState {
                ProgressView().scaleEffect(1.5)
            }
        }
    }
    
    func listView() -> some View {

        List($appVM.appModels) { appModel in
           ListItemView(appModel: appModel)
           
        }
        .listStyle(.plain)
        .navigationTitle("App")
        .refreshable(action: {
            await appVM.refreshApp()
        })
        .pullUpLoading(listPullState: $appVM.listPullState, onLoad: {
            appVM.loadMore()
        })
        .background(Color(.secondarySystemBackground))
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        AppContentView()
    }
}



