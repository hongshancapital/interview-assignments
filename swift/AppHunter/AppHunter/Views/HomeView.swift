//
//  HomeView.swift
//  AppHunter
//
//  Created by zhang shijie on 2023/5/24.
//

import SwiftUI

struct HomeView: View {
    @EnvironmentObject var viewModel: HomeViewModel

    private func reload() async throws {
        try await Task.sleep(until: .now + .seconds(0.5), clock: .continuous)
        viewModel.reload()
    }

    var body: some View {
        Group {
            switch viewModel.state {
            case .loading:
                ProgressView()
            case let .failed(error):
                ErrorView(error: error, handler: viewModel.reload)
            case .success:
                NavigationView {
                    ScrollView(.vertical) {
                        LazyVStack {
                            ForEach(0 ..< viewModel.results.count, id: \.self) { index in
                                let item = $viewModel.results[index]
                                AppInfoRow(item: item, clickCollectBlock: { item in
                                    viewModel.addCollection(item: item)
                                }).background(Color.white)
                                    .cornerRadius(12)
                                    .padding(EdgeInsets(top: 0, leading: 12, bottom: 6, trailing: 12))
                            }
                            RefreshFooter(noMoreData: $viewModel.noMoreData) {
                                do {
                                    try await viewModel.loadNext()
                                } catch {}
                            }
                        }

                    }.background(Color(hex: 0xF2F2F7)).navigationTitle(Text("App")).refreshable {
                        do {
                            try await self.reload()
                        } catch {}
                    }
                }
            }
        }.onAppear(perform: viewModel.reload)
    }
}

struct HomeView_Previews: PreviewProvider {
    static var previews: some View {
        HomeView()
    }
}
