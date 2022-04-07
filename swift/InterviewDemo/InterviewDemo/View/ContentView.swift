//
//  ContentView.swift
//  InterviewDemo
//
//  Created by Chenjun Ren on 2022/4/1.
//

import SwiftUI

struct ContentView: View {
    @StateObject var viewModel = ContentViewModel(appInfoRepository: MockRepository(numberPerFetch: 10))
    
    var body: some View {
        NavigationView {
            List {
                Section {
                    ForEach(viewModel.appInfos, id: \.id) { appInfo in
                        ListEntry(entryItem: appInfo)
                            .listRowBackground(Color.clear)
                            .listRowSeparator(.hidden)
                            .environmentObject(viewModel)
                            .onAppear {
                                viewModel.fetchMoreIfNeeded(current: appInfo)
                            }
                    }
                } footer: {
                    listFooter
                        .listRowBackground(Color.clear)
                        .listRowSeparator(.hidden)
                        .frame(maxWidth: .infinity, alignment: .center)
                }
                
            }
            .background(Color(uiColor: .secondarySystemBackground))
            .overlay {
                if viewModel.isLoading && viewModel.appInfos.isEmpty {
                    ProgressView()
                        .frame(maxWidth: .infinity, maxHeight: .infinity)
                }
            }
            .environment(\.defaultMinListRowHeight, 0)
            .listStyle(.plain)
            .navigationTitle("App")
            .refreshable {
                await viewModel.refresh()
            }
        }
    }
    
    var listFooter: some View {
        Group {
            if viewModel.isLoading && !viewModel.appInfos.isEmpty {
                HStack(spacing: 5) {
                    ProgressView()
                    Text("Loading...")
                }
            }
            
            if let error = viewModel.error {
                Text(error.localizedDescription)
            }
            
            if !viewModel.hasMore {
                Text("No more data.")
            }
        }
        .foregroundColor(.secondary)
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
