//
//  AppListView.swift
//  Demo
//
//  Created by GNR on 10/27/22.
//

import SwiftUI

struct AppListView: View {
    @StateObject var viewModel = AppsViewModel()
    
    private var refreshFooter: some View {
        HStack(alignment: .center) {
            Spacer()
            if viewModel.isLoadMore {
                ProgressView()
                    .frame(width: 20, height: 20)
            }
            Text(viewModel.isLoadMore ? "Loading..." : "No more data.")
                .foregroundColor(.gray)
            Spacer()
        }
        .listRowBackground(Color.bgColor)
        .listRowSeparator(.hidden)
        .padding(.top, 12)
    }
    
    private var networkErrorView: some View {
        VStack {
            Spacer()
            Text("Network error, please retry...")
                .font(.headline)
                .foregroundColor(.gray)
                .padding()
            Button("Retry") {
                Task {
                    await viewModel.loadNewApps()
                }
            }
            .buttonStyle(.bordered)
            Spacer()
        }
    }
    
    private var emptyView: some View {
        VStack {
            Spacer()
            Text("No data, please retry...")
                .font(.headline)
                .foregroundColor(.gray)
                .padding()
            Button("Retry") {
                Task {
                    await viewModel.loadNewApps()
                }
            }
            .buttonStyle(.bordered)
            Spacer()
        }
    }
    
    private var appsList: some View {
        List {
            ForEach(viewModel.apps) { app in
                LazyVStack(alignment: .leading) {
                    AppRowView(app: Binding(
                        get: { app },
                        set: { _ in
                            Task {
                                await viewModel.favoriteApp(app)
                            }
                        }
                    ))
                    .padding(12)
                }
                .listRowSeparator(.hidden)
                .listRowBackground(Color.bgColor)
                .listRowInsets(EdgeInsets(top: 12, leading: 12, bottom: 0, trailing: 12))
                .background(.white)
                .cornerRadius(12)
                .task {
                    await viewModel.itemOnAppear(app)
                }
                
                if viewModel.isLastItem(app) {
                    refreshFooter
                }
            }
        }
        .listStyle(.grouped)
    }
    
    var body: some View {
        NavigationView {
            Group {
                if viewModel.isProgressing {
                    ProgressView()
                } else {
                    if viewModel.apps.isEmpty {
                        if let _ = viewModel.error {
                            networkErrorView
                        } else {
                            emptyView
                        }
                    } else {
                        appsList
                    }
                }
            }
            .navigationTitle(Text("App"))
        }
        .task {
            await viewModel.loadNewApps()
        }
        .refreshable {
            await viewModel.loadNewApps()
        }
    }
}

struct AppListView_Previews: PreviewProvider {
    static var previews: some View {
        AppListView(viewModel: AppsViewModel())
    }
}
