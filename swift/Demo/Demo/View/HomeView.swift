//
//  HomeView.swift
//  Demo
//
//  Created by 杨立鹏 on 2022/3/30.
//

import SwiftUI

struct HomeView: View {
    @StateObject private var homeViewModel = HomeViewModel()

    var body: some View {
        NavigationView {
            Group {
                if homeViewModel.items.isEmpty {
                    ProgressView()
                } else {
                    List {
                        ForEach(homeViewModel.items, id: \.self) { item in
                            AppItemView(item: item, isLiked: homeViewModel.likedItems.contains(item)) {
                                homeViewModel.toggleLikeState(item: item)
                            }
                            .listRowSeparatorTint(.clear)
                            .listRowBackground(Color.clear)
                        }
                        LoadMoreView()
                    }
                    .listStyle(.plain)
                }
            }
            .background(Color.bgColor)
            .task {
                await homeViewModel.getData()
            }
            .refreshable {
                await homeViewModel.getData(isRefreshData: true)
            }
            .navigationTitle("App")
        }
    }

    @ViewBuilder
    /// 加载更多的视图
    func LoadMoreView() -> some View {
        Group {
            if homeViewModel.resultCount > homeViewModel.items.count {
                HStack(spacing: 4) {
                    ProgressView()
                    Text("Loading...")
                }
                .task {
                    await homeViewModel.getData()
                }
            } else {
                Text("No more data.")
            }
        }
        .frame(maxWidth: .infinity, alignment: .center)
        .foregroundColor(.gray)
        .listRowBackground(Color.clear)
        .listRowSeparatorTint(.clear)
        .listRowInsets(EdgeInsets(top: 0, leading: 0, bottom: 0, trailing: 0))
        .offset(y: -10)
    }
}

struct HomeView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
