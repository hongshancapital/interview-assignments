//
//  AppListView.swift
//  APPList
//
//  Created by three on 2023/4/10.
//

import SwiftUI

struct AppListView: View {
    
    @ObservedObject private var viewModel = AppListViewModel()
    
    init() {
        URLProtocol.registerClass(MockURLProtocol.self)
    }
    
    var body: some View {
        NavigationView {
            List(viewModel.apps) { app in
                AppListItemView(item: app, favouriteToggle: { trackId in
                    viewModel.updateFavourite(trackId: trackId)
                })
                    .listRowSeparator(.hidden)
                    .listRowBackground(Color.clear)
                    .listRowInsets(EdgeInsets(top: 5, leading: 14, bottom: 5, trailing: 14))
                    
                
                if app.id == viewModel.apps.last?.id {
                    LoadingView(loadingState: $viewModel.loadingState)
                        .listRowBackground(Color.clear)
                        .listRowSeparator(.hidden)
                        .frame(maxWidth: .infinity)
                        .frame(height: 30)
                        .onAppear {
                            self.viewModel.loadMoreData()
                        }
                }
            }
            .scrollContentBackground(.hidden)
            .background(Color(.secondarySystemBackground))
            .listStyle(.inset)
            .navigationTitle("App")
            .onAppear(perform: {
                self.viewModel.reloadData()
            })
            .refreshable {
                self.viewModel.reloadData()
            }
        }
    }
}
