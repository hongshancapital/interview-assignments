//
//  AppListView.swift
//  ItuneApps
//
//  Created by daicanglan on 2023/3/27.
//

import SwiftUI

struct AppListView: View {
    @StateObject var vm: AppViewModel = AppViewModel()
    var body: some View {
        if vm.apps == nil {
            if vm.error == nil {
                ProgressView()
                    .onAppear {
                        self.vm.loadApps()
                    }
            } else {
                Text(vm.error?.desc ?? "其他错误")
            }
        } else {
            List {
                ForEach(Array(vm.results.enumerated()), id: \.1.id) { _, model in
                    AppRowView(model: model, isLike: vm.likeAppIds.contains(model.trackId))
                        .frame(height: 70)
                        .listRowSeparator(.hidden)
                        .listRowBackground(
                            RoundedRectangle(cornerRadius: 15)
                                .foregroundColor(.white)
                                .padding(6))
                }

                bottomLoadView
                    .listRowBackground(Color.clear)
            }
            .refreshable {
                vm.loadApps()
            }
            .environmentObject(vm)
        }
    }

    var bottomLoadView: some View {
        HStack {
            Spacer()
            HStack(spacing: 10) {
                if !vm.isNoMore {
                    ProgressView().onAppear {
                        vm.loadMore()
                    }
                }

                Text(vm.isNoMore ? "No More Data" : "Loading...").foregroundColor(.secondary)
            }
            Spacer()
        }
    }
}

struct AppListView_Previews: PreviewProvider {
    static var previews: some View {
        HomeScreen().environmentObject(AppViewModel())
    }
}
