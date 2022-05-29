//
//  ContentView.swift
//  InterviewPreWork
//
//  Created by jeffy on 2022/5/12.
//

import SwiftUI

struct ContentView: View {

    @ObservedObject var viewModel = AppViewModel()
    @Environment(\.refresh) private var refreshAction
    @State private var isRefreshing = false
    
    var body: some View {
        NavigationView {
            Group {
                if let response = viewModel.source {
                    List {
                        ForEach(response.results) { item in
                                ListItemView(item: item)
                        }
                        .listRowBackground(EmptyView())
                        .listRowSeparator(.hidden, edges: .all)
                        .textSelection(.disabled)

                        ListFooterView(noMoreData: viewModel.noMore)
                                        .listRowBackground(EmptyView())
                                        .offset(x: 0, y: -10)
                                        .frame(height: 60, alignment: .center)
                                        .onAppear {
                                            viewModel.fakeLoadMore()
                                        }
                    }
                    .listStyle(.plain)
                } else {
                    Text("Loading...")
                        .onAppear() { self.viewModel.fakeReload() }
                }
                
            }
            .background(Color(white: 0.9))
            .navigationTitle("App")

        }
        .refreshable {
            viewModel.fakeReload()
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}

