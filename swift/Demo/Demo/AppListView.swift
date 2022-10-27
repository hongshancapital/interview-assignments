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
    
    var body: some View {
        NavigationView {
            if viewModel.isRefreshing && viewModel.apps.count == 0 {
                ProgressView()
                    .navigationTitle(Text("App"))
            } else {
                List {
                    ForEach(viewModel.apps, id: \.self) { app in
                        LazyVStack(alignment: .leading) {
                            AppRowView(app: app)
                                .environmentObject(viewModel)
                                .padding(12)
                        }
                        .listRowSeparator(.hidden)
                        .listRowBackground(Color.bgColor)
                        .listRowInsets(EdgeInsets(top: 12, leading: 12, bottom: 0, trailing: 12))
                        .background(.white)
                        .cornerRadius(12)
                        .onAppear {
                            viewModel.itemOnAppear(app)
                        }
                        
                        if viewModel.isLastItem(app) {
                            refreshFooter
                        }
                    }
                }
                .listStyle(.grouped)
                .navigationTitle(Text("App"))
            }
        }
        .environmentObject(viewModel)
        .onAppear(perform: viewModel.loadNewApps)
        .refreshable {
            viewModel.loadNewApps()
        }
    }
}

struct AppListView_Previews: PreviewProvider {
    static var previews: some View {
        AppListView(viewModel: AppsViewModel())
    }
}
