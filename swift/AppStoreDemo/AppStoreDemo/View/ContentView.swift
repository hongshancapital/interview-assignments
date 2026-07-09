//
//  ContentView.swift
//  AppStoreDemo
//
//  Created by wuxi on 2023/3/18.
//

import SwiftUI
import Combine

struct ContentView: View {
    
    @EnvironmentObject var store: Store
    var state: AppState { store.state }
    
    var body: some View {
        NavigationView {
            content.navigationTitle("App")
        }
    }
    
    private var content: some View {
        let status = state.status
        return Group {
            switch status {
            case .firstLoading:
                ProgressView()
                    .onAppear {
                        store.dispatch(.refresh())
                    }
            case .error(let err):
                VStack {
                    Text("错误: \(err.localizedDescription)")
                    Button {
                        store.dispatch(.refresh())
                    } label: {
                        Text("重试")
                    }
                }
            case .loadingMore, .normal, .refreshing, .noMoreData:
                List {
                    ForEach(state.data) { item in
                        ItemCell(item: item)
                    }
                    footer
                }
                .refreshable {
                    await store.asyncDispatch(.refresh())
                }
            }
        }
    }
    
    private var footer: some View {
        let status = state.status
       
        return HStack {
            if case .loadingMore = status {
                ProgressView()
            }
            if case .loadingMore = status {
                Text("  Loading...")
            } else if case .noMoreData = status {
                Text("No more data")
            } else {
                Text("")
                    .onAppear {
                        store.dispatch(.loadMore)
                    }
            }
        }
        .foregroundColor(Color.gray)
        .frame(maxWidth: 1000, alignment: .center)
        .listRowBackground(
            Color.clear
        )
        .listRowSeparator(.hidden)
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView().environmentObject(Store())
    }
}
