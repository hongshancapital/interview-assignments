//
//  ContentView.swift
//  SocialAppListSwiftUI
//
//  Created by luobin on 2022/3/25.
//

import SwiftUI

struct SocialAppListView: View {
    
    @MainActor
    @StateObject private var viewModel = SocialAppListViewModel()

    var body: some View {
        NavigationView {
            List {
                ForEach($viewModel.appList) { appModel in
                    SocialAppListRow(appModel: appModel)
                        .listRowSeparator(.hidden)
                        .listRowBackground(Color.clear)
                        .listRowInsets(.init(top: 5, leading: 15, bottom: 5, trailing: 15))
                }
                
                // Show more loading
                HStack(alignment: .center, spacing: 5) {
                    Spacer()
                    // Refresh success, should check if show load more
                    if viewModel.refreshState == .succeed,
                       viewModel.isHasMore {
                        ProgressView()
                        Text("Loading...")
                        .foregroundColor(Color("TextSecondaryColor"))
                    } else if viewModel.refreshState == .succeed {
                        Text("No more data.")
                        .foregroundColor(Color("TextSecondaryColor"))
                    }
                    Spacer()
                }
                .frame(height: 30)
                .task {
                    if viewModel.isHasMore {
                        await viewModel.loadMore()
                    }
                }
                .listRowSeparator(.hidden)
                .listRowBackground(Color.clear)
            }
            .refreshable {
                await viewModel.refresh()
            }
            .listStyle(.plain)
            .navigationBarTitle(Text("App"), displayMode: .large)
            .background(Color("WindowBackgroundColor"))
            .overlay(content: {
                // Refresh error, show error view
                if case .error(let error) = viewModel.refreshState {
                    Text("\(error.localizedDescription)")
                        .padding(30)
                        .foregroundColor(Color("TextPrimaryColor"))
                }
                // Show progress when first load data
                else if viewModel.refreshState == .loading && viewModel.isFirstLoading {
                    ProgressView()
                }
            })
            .task {
                await viewModel.refresh()
            }
        }
        .navigationViewStyle(.stack)
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        SocialAppListView()
    }
}


