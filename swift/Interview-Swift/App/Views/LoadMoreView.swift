//
//  LoadMoreView.swift
//  App
//
//  Created by lizhao on 2022/9/21.
//

import SwiftUI

struct LoadMoreView: View {
    @EnvironmentObject var store: Store
    var body: some View {
        LoadingView {
            Color.clear
        }
        .onAppear {
            self.store.dispatch(.loadApplist)
        }
        .listRowBackground(Color.clear)
    }
}

struct LoadingView<Content>: View where Content: View {
    
    @EnvironmentObject var store: Store
    var content: () -> Content
    
    var body: some View {
        GeometryReader { geometry in
            ZStack(alignment: .center) {
                self.content()
                    .disabled(store.appState.appList.loadmoreState == .loading)
                HStack {
                    switch store.appState.appList.loadmoreState {
                    case .hidden:
                        EmptyView()
                    case .loading:
                        ProgressView()
                        Text(store.appState.appList.loadmoreState.description)
                    case .noMoreData:
                        Text(store.appState.appList.loadmoreState.description)
                    }
                }
            }
        }
    }
}

struct LoadMoreView_Previews: PreviewProvider {
    static var previews: some View {
        LoadingView  {
            LoadMoreView()
        }.environmentObject(Store())
    }
}
