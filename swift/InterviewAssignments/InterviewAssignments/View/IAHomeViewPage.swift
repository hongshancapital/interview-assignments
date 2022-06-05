//
//  IAHomePage.swift
//  InterviewAssignments
//
//  Created by ZhuChaoJun on 2022/6/2.
//

import SwiftUI

struct IAHomeViewPage: View {

    @StateObject var viewModel = IAHomeViewModel()

    var body: some View {
        NavigationView {
            Group {
                if viewModel.isLoading {
                    ProgressView().progressViewStyle(.circular)
                } else {
                    appListView
                }
            }
            .navigationTitle("App")
            .task { await viewModel.initDataSources() }
        }
    }
}

extension IAHomeViewPage {

    var appListView: some View {

        LoadableList(noMoreData: $viewModel.noMoreData) {
            ForEach(viewModel.cellViewModels) { vm in
                IAHomeCellView(viewModel: vm)
            }
        }
        .loadable { await viewModel.loadMore() }
        .refreshable { await viewModel.refresh() }
    }
}

struct IAHomePage_Previews: PreviewProvider {
    static var previews: some View {
        IAHomeViewPage()
    }
}
