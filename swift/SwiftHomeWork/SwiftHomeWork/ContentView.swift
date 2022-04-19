//
//  ContentView.swift
//  SwiftHomeWork
//
//  Created by apple on 2022/4/12.
//

import SwiftUI
import Combine

struct ContentView: View {
    @State private var showError = false
    @State private var err: Error?
    @EnvironmentObject var viewModel: DataViewModel

    func refresh() {
        Task {
            do {
                try await viewModel.refresh()
            } catch {
                self.err = error
                self.showError = true
            }
        }
    }

    func loadMore() {
        Task {
            do {
                try await viewModel.loadMore()
            } catch {
                self.err = error
                self.showError = true
            }
        }
    }

    var body: some View {
        VStack {
            if !viewModel.apps.isEmpty {
                List {
                    ForEach(viewModel.apps) { item in
                        ItemUIView.init(item: item)
                            .listRowSeparator(.hidden)
                            .listRowInsets(EdgeInsets.init(top: 0,
                                                           leading: 0,
                                                           bottom: 10,
                                                           trailing: 0))
                            .listRowBackground(Color.clear)
                            .buttonStyle(.borderless)
                    }
                    LoadingFooter(isEnd: viewModel.isEnd)
                        .listRowBackground(Color.clear)
                        .onAppear {
                            if !viewModel.isEnd {
                                self.loadMore()
                            }
                        }
                }
            } else {
                ProgressView()
            }
        }
        .alert(isPresented: $showError) {
            Alert.init(title: Text((self.err?.localizedDescription) ?? "unknown error"),
                       message: nil,
                       primaryButton: Alert.Button.destructive(Text("retry"), action: {
                if self.viewModel.apps.isEmpty {
                    self.refresh()
                } else {
                    self.loadMore()
                }
            }),
                       secondaryButton: .cancel()
            )
        }
        .refreshable {
            self.refresh()
        }
        .onAppear(perform: {
            self.refresh()
        })
        .navigationTitle("App")
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        NavigationView {
            ContentView()
        }.environmentObject(DataViewModel(MockNetService.init()))
    }
}
