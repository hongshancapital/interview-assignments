//
//  ContentView.swift
//  InterviewDemo
//
//  Created by Lays on 2023/3/15.
//

import SwiftUI

struct ContentView: View {
    @StateObject var viewModel: ViewMoel = ViewMoel(service: AppService(pageSize: 10))
    
    var body: some View {
        NavigationView {
            List {
                ForEach(viewModel.appList, id: \.trackId) { model in
                    ListRowView(rowItem: model)
                        .listRowBackground(Color.clear)
                        .listRowSeparator(.hidden)
                        .environmentObject(viewModel)
                        .onAppear {
                            viewModel.fetchMoreData(currentItem: model)
                        }
                }
                footer
                    .listRowBackground(Color.clear)
                    .listRowSeparator(.hidden)
                    .frame(maxWidth: .infinity, alignment: .center)
            }
            .background(Color(UIColor.secondarySystemBackground))
            .listStyle(.plain)
            .navigationTitle("App")
            .refreshable {
                await viewModel.refresh()
            }
            .overlay {
                if viewModel.isLoading && viewModel.appList.isEmpty {
                    ProgressView().frame(maxWidth: .infinity, maxHeight: .infinity)
                }
            }
        }
    }
    
    var footer: some View {
        Group {
            if let error = viewModel.error {
                Text(error.localizedDescription)
            }else {
                if !viewModel.appList.isEmpty {
                    HStack(spacing: 8) {
                        if viewModel.isLoading {
                            ProgressView()
                        }
                        Text(viewModel.hasMore ?  "Loading..." : "No more data.")
                    }
                }
            }
        }.foregroundColor(.secondary)
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
