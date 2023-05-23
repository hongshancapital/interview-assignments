//
//  ContentView.swift
//  Applist
//
//  Created by santcool on 2023/1/22.
//

import SwiftUI

@available(iOS 15.0, *)
struct ContentView: View {
    @StateObject var viewModel: AppListViewModel = AppListViewModel(page: 0)
    var body: some View {
        NavigationView {
            Group {
                if viewModel.firstLoad {
                    if viewModel.loadError != nil && viewModel.resultData?.results.count ?? 0 <= 0 {
                        Button {
                            Task {
                                await viewModel.refresh()
                            }

                        } label: {
                            Text("重试")
                                .background(Color.white)
                        }
                    } else {
                        ProgressView().offset(y: -60)
                            .background(Color.white)
                    }
                } else {
                    List {
                        ForEach(viewModel.resultData?.results ?? [], id: \.trackId) { (appModel: AppModel) in
                            let isLiked = viewModel.likedList.first(where: { $0.trackId == appModel.trackId }) != nil
                            ApplistCell(viewModel: viewModel, appModel: appModel, isLiked: isLiked)
                                .listRowSeparator(.hidden)
                                .listRowInsets(EdgeInsets(top: 5, leading: 0, bottom: 5, trailing: 0))
                                .listRowBackground(Color.clear)
                        }
                        if viewModel.resultData?.results.count ?? 0 > 0 {
                            LoadingMoreView(viewModel: viewModel)
                                .listRowSeparator(.hidden)
                                .listRowBackground(Color.clear)
                        }
                    }
                    .listStyle(.plain)
                    .refreshable {
                        await viewModel.refresh()
                    }
                }
            }
            .navigationTitle("App")
            .navigationViewStyle(.stack)
            .background(Color("gray_bg"))
        }
        .task {
            await viewModel.refresh()
        }
        .alert(viewModel.loadError?.localizedDescription ?? "Network failed，please try later.", isPresented: .constant(viewModel.loadError != nil), actions: {})
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
