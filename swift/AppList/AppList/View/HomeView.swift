//
//  ContentView.swift
//  AppList
//
//  Created by jay on 2022/2/22.
//

import SwiftUI
import Kingfisher

struct HomeView: View {
    
    @StateObject var viewModel = HomeViewModel()
    @State var offsetY: CGFloat = 0.0
    
    var body: some View {
        NavigationView {
            if viewModel.loadingStatus == .idle {
                noNetworkView
            }else if viewModel.loadingStatus == .loading {
                loadingView
            }else {
                listView
            }
        }
        .background(viewModel.loadingStatus == .loaded ? Color("background") : Color.clear )
        .onAppear {
            viewModel.getData()
        }
    }
    
    var noNetworkView: some View {
        VStack {
            Text("无网络，点击重试")
                .frame(width: 200, height: 200)
                .offset(y: -60)
                .modifier(NavigationStyleModifier(title: "App"))
                .onTapGesture {
                    viewModel.reload()
                }
        }
    }
    
    var loadingView: some View {
        ZStack {
            ProgressView()
                .offset(y: -60)
                .modifier(NavigationStyleModifier(title: "App"))
        }
    }
    
    var listView: some View {
        List(viewModel.listDatas) { item in
            ListRow(item: item, favorAction: { viewModel.toggleFavor(for: $0) })
            if viewModel.listDatas.isLastItem(item) {
                footer()
                safeAreaView
            }
        }
        .listStyle(.plain)
        .padding(.horizontal, 0)
        .refreshable {
            viewModel.reload()
        }
        .modifier(NavigationStyleModifier(title: "App"))
        .background(Color("background"))
    }
    
    // 忽略了下面的安全区，最底部插入一个透明 View，把 List 挤上来， 以防止list 被遮住
    var safeAreaView: some View {
        AnyView(Color.clear).frame(height: UIScreen.homeIndicatorHeight)
            .listRowSeparator(.hidden)
            .listRowBackground(Color.clear)
    }
    
    func footer() -> some View {
        RefreshFooter(state: viewModel.noMoreData ? .noMoreData(message: "No more data.") : .loading(message: "Loading"))
            .listRowSeparator(.hidden)
            .listRowBackground(Color.clear)
            .onAppear {
                viewModel.getData()
            }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        HomeView()
    }
}
